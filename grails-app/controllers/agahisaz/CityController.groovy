package agahisaz

import grails.converters.JSON

class CityController {

    def mongoService

    def search() {
        def cities = mongoService.getCollection('province').aggregate(
                [$unwind: '$cities'],
                [$match: [
                        'cities.name': [$regex: ".*${params.id}.*"]
                ]],
                [$project: [province: '$name', city: '$cities.name']]
        ).results()
        render(cities.collect { [name: "${it.province} - ${it.city}", cityName: it.city] } as JSON)
    }
}
