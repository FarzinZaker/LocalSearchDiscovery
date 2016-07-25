package agahisaz

import utils.StringHelper

class CityService {

    def getAllData() {
        Province.findAll().collect {
            [
                    name  : it.name,
                    cities: it.cities.collect {
                        [
                                name    : it.name,
                                location: it.location
                        ]
                    }.sort { it.name }
            ]
        }.sort { it.name }
    }

    def findProvinceByCity(String city) {
        allData.find { it.cities.any { it.name == city } }
    }

    def importFromFile() {
        def lines = CategoryService.classLoader.getResource('data/cities.csv').readLines('UTF-8')
        lines.remove(0)
        lines.each {
            def line = StringHelper.normalize(it).split(',')
            def provinceName = line[0].trim()
            def province = Province.findByName(provinceName)
            if (!province) {
                province = new Province(name: provinceName).save(flush: true)
            }
            def cityName = line[1].trim()
            def city
            if (!province.cities.any { Map c -> c.name == cityName }) {
                if (!province.cities)
                    province.cities = new ArrayList()
                city = [name: cityName]
                province.cities.add(city)
            } else
                city = province.cities.find { Map c -> c.name == cityName }
            city["location"] = [lat: line[3].trim().toDouble(), long: line[2].trim().toDouble()]
//            city["latitude"] = line[3].trim().toDouble()
//            city["longitude"] = line[2].trim().toDouble()
            province.save(flush: true)
        }
    }
}
