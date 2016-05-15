package agahisaz

import cache.CategoryCache

class PlaceService {

    def mongoService

    def similarPlaces(Place place) {

        def time = new Date().time
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
                [$limit: 5]
        ).results()
        println(new Date().time - time)
        list.collect { it._id }.each {
            it.category = CategoryCache.findCategory(it.category)
        }
    }
}
