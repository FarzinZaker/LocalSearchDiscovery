package agahisaz

import cache.CategoryCache
import com.pars.agahisaz.User

class PlaceService {

    def mongoService
    def springSecurityService

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
        if(!place)
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
}
