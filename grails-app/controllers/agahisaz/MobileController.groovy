package agahisaz

import cache.CategoryCache
import com.mongodb.DBCursor
import grails.converters.JSON

class MobileController {

    def mongoService

    def topCategories() {
        render(Category.findAllByParentIsNull([max: 5]).collect {
            [
                    id  : it.id,
                    name: it.name
            ]
        }?.reverse() as JSON)
    }

    def search() {
        def sort = [:]
        def query = [:]
        def aggregateQuery
        def tags
        if (params.tags)
            tags = params.tags?.split('[|]')?.toList() ?: []
        def projection = null
        if (params.province && params.city)
            query << [province: params.province]
        if (params.city)
            query << [city: params.city]

        def queryString = params.id?.toString()?.trim() ?: ''
        request.setAttribute("query", queryString)
        def category = Category.findByName(queryString)
        if (category) {
            request.setAttribute("queryCategory", category?.id)
            queryString = ''
            query << [category: [$in: CategoryCache.findCategory(category.id)?.childIdList ?: [] + [category.id]]]
        }
        if (tags)
            query << [tags: [$all: tags]]

        if (queryString != '') {
            request.setAttribute("queryCategory", "0")
            //search using geoWithin
            query << [$text: [$search: params.id]]
            projection = [score: [$meta: "textScore"]]
            sort << ['score': ['$meta': 'textScore']]
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
            aggregateQuery = query.clone()
        } else {
            //search using geoNear
            aggregateQuery = query.clone()
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [
                            $near       : [latitude, longitude],
                            $maxDistance: 0.03D
                    ]
                    ]
                    aggregateQuery << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
        }

        def cursor = mongoService.getCollection("place").find(query, projection)?.sort(sort)?.limit(50)
        def places = []
        try {
            places = cursor?.findAll()?.each { place -> place.category = CategoryCache.findCategory(place.category) } ?: []
        }
        finally {
            cursor?.close()
        }
        def newTags = []
        if (places?.size()) {
            if (tags?.size())
                newTags = mongoService.getCollection("place").aggregate([$match: aggregateQuery], [$unwind: '$tags'], [$match: [tags: [$nin: tags]]], [$group: [_id: '$tags', count: [$sum: 1]]], [$sort: ['count': -1]], [$limit: 10])?.results()
            else
                newTags = mongoService.getCollection("place").aggregate([$match: aggregateQuery], [$unwind: '$tags'], [$group: [_id: '$tags', count: [$sum: 1]]], [$sort: ['count': -1]], [$limit: 10])?.results()
        }
        render([places: places, tags: newTags, currentTags: tags] as JSON)
    }
}
