package agahisaz

import cache.CategoryCache
import com.mongodb.QueryBuilder
import com.pars.agahisaz.User
import grails.converters.JSON
import org.apache.commons.collections.Closure
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.client.Client
import org.elasticsearch.common.unit.DistanceUnit
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.ScriptQueryBuilder
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptService
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms

import javax.management.Query

class PlaceService {

    def mongoService
    def springSecurityService
    def elasticSearchService

    def save(Map params) {
        def place = new Place()
        place.name = params.name.trim()
        place.province = params.province
        place.city = params.city
        place.address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
        place.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        place.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        place.location = params.location.toString().split(',').collect { it.toDouble() }
        place.category = Category.findByName(params.category3?.toString() ?: params.category2?.toString())
        place.creator = springSecurityService.currentUser as User
        place.tags = new ArrayList()
        params.tags?.trim()?.split(',')?.each { String tagName ->
            if (tagName?.trim() != '')
                place.tags.add(tagName.trim())
        }

        return place.save(flush: true)
    }

    def edit(Map params) {
        def place = Place.get(params.id)
        if (!place)
            return [errors: 'NO SUCH PLACE']
        place.name = params.name.trim()
        place.province = params.province
        place.city = params.city
        place.address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
        place.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        place.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        place.location = params.location.toString().split(',').collect { it.toDouble() }
        place.category = Category.findByName(params.category3?.toString() ?: params.category2?.toString())
        place.creator = springSecurityService.currentUser as User
        place.tags = new ArrayList()
        params.tags?.trim()?.split(',')?.each { String tagName ->
            if (tagName?.trim() != '')
                place.tags.add(tagName.trim())
        }
        place.approved = false
        place.reportType = null
        place.reportComment = null

        return place.save(flush: true)
    }

    def similarPlaces(Place place, Integer itemsCount = 5) {

//        def time = new Date().time
        def list = mongoService.getCollection('place').aggregate(
                [$match: [
                        province: place.province,
                        city    : place.city,
                        category: place.categoryId,
                        _id     : [$ne: place?.id]
                ]
                ],
                [$unwind: '$tags'],
                [$match: [
                        tags: [$in: place.tags]

                ]
                ],
                [$group: [
                        _id  : [id: '$_id', name: '$name', phone: '$phone', address: '$address', averageRate: '$averageRate', ratesCount: '$ratesCount', category: '$category'],
                        count: [$sum: 1]
                ]
                ],
                [$sort: [count: -1]],
                [$limit: itemsCount]
        ).results()
//        println(new Date().time - time)
        list.collect { it._id }.each {
            it.category = CategoryCache.findCategory(it.category)
        }
    }

    def similarPlacesGlobal(Place place, Integer itemsCount = 5) {

//        def time = new Date().time
        def list = mongoService.getCollection('place').aggregate(
                [$match: [
                        _id: [$ne: place?.id]
                ]
                ],
                [$unwind: '$tags'],
                [$match: [
                        tags: [$in: place.tags]

                ]
                ],
                [$group: [
                        _id  : [id: '$_id', name: '$name', phone: '$phone', address: '$address', averageRate: '$averageRate', ratesCount: '$ratesCount', category: '$category'],
                        count: [$sum: 1]
                ]
                ],
                [$sort: [count: -1]],
                [$limit: itemsCount]
        ).results()
//        println(new Date().time - time)
        list.collect { it._id }.each {
            it.category = CategoryCache.findCategory(it.category)
        }
    }

    def search(Map params) {
        def tags = []
        if (params.tags)
            tags = params.tags?.split('[|]')?.toList() ?: []

        def queryString = params.id?.toString()?.replace('-', ' ')?.trim() ?: null
        def specialChars = ['-', ')', '(', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '_', '+', '=', '{', '}', '\\', '[', ']', '|', ';', ':', '\'', '"', '?', '/', ',', '.', '<', '>']
        specialChars.each { queryString = queryString.replace(it, ' ') }

        def nearParams = []
        if (params.near) {
            nearParams = params.near?.toString()?.split(',')?.collect { it?.toDouble() }?.findAll { it }
        }

        def query = QueryBuilders.boolQuery()
        query = query.must(QueryBuilders.termQuery('approved', true))
        if (queryString)
            query = query.must(QueryBuilders.queryStringQuery(queryString))
        if (params.province && params.city)
            query = query.must(QueryBuilders.termQuery('province', params.province))
        if (params.city)
            query = query.must(QueryBuilders.termQuery('city', params.city))
        if (params.address)
            query = query.must(QueryBuilders.termQuery('address', params.address))
        tags?.each { tag ->
            query = query.must(QueryBuilders.termQuery('tags', tag))
        }
        query = query.must(QueryBuilders.missingQuery('reportType'))
        if (nearParams?.size() == 2)
            query = query.filter(QueryBuilders.geoDistanceQuery('locationString').distance(5.0, DistanceUnit.KILOMETERS).lat(nearParams?.first() as Double).lon(nearParams?.last() as Double))


        def result
        elasticSearchService.elasticSearchHelper.withElasticSearch { Client client ->
            result = client
                    .prepareSearch()
                    .setSearchType(SearchType.DFS_QUERY_AND_FETCH)
                    .setQuery(query)
            if (nearParams?.size() == 2)
                result = result.addScriptField('distance', new Script("doc['locationString'].arcDistanceInKm(lat, lon)", ScriptService.ScriptType.INLINE, 'groovy',
                        [
                                "lat": nearParams?.first(),
                                "lon": nearParams?.last()
                        ]))
            result = result.addFields('name', 'location', 'address', 'phone', 'category.name', 'category.id')
                    .execute()
                    .actionGet()
        }

        def places = result.hits.hits.collect {
            [
                    id      : it?.id,
                    name    : it?.fields?.name?.find(),
                    location: it?.fields?.location?.toList(),
                    address : it?.fields?.address?.find(),
                    phone   : it?.fields?.phone?.find(),
                    category: [
                            id  : it?.fields["category.id"]?.find(),
                            name: it?.fields["category.name"]?.find()
                    ],
                    distance: it?.fields?.distance?.find()
            ]
        }

        def newTags
        elasticSearchService.elasticSearchHelper.withElasticSearch { Client client ->
            newTags = client.prepareSearch().setSize(0).addAggregation(AggregationBuilders.filter('tagAggregates')
                    .filter(query)
                    .subAggregation(AggregationBuilders.terms('tagList').field('tags')))
                    .execute().actionGet();
        }

        newTags = (newTags.aggregations.tagAggregates.aggregations.tagList as StringTerms).buckets.collect {
            [name: it.key, count: it.docCount]
        }.findAll { !tags.contains(it.name) }

        [places: places, tags: newTags, currentTags: tags]
    }
}