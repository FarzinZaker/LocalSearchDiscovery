package agahisaz

class MapTagLib {

    static namespace = "map"

    def locationPicker = { attrs, body ->
        out << render(template: '/common/map/locationPicker', model: [name: attrs.name, id: attrs.id ?: attrs.name, cssClass: attrs.cssClass, style: attrs.style, center: attrs.center, visitorLocation: attrs.visitorLocation == '1', width: attrs.width, height: attrs.height, validation: attrs.validation])
    }

    def locationViewer = { attrs, body ->
        out << render(template: '/common/map/locationViewer', model: [place: attrs.place as Place, cssClass: attrs.cssClass, style: attrs.style, location: attrs.location, width: attrs.width, height: attrs.height])
    }

    def directionLink = { attrs, body ->
        out << render(template: '/common/map/directionLink', model: [place: attrs.place as Place])
    }

    def explore = { attrs, body ->
        def places = attrs.places?.findAll { it.location }
        def center = attrs.center
        def maxLatitude = places.collect { it.location[0] }.max() ?: 0
        def minLatitude = places.collect { it.location[0] }.min() ?: 0
        def maxLongitude = places.collect { it.location[1] }.max() ?: 0
        def minLongitude = places.collect { it.location[1] }.min() ?: 0
        if (!center) {
            center = [(minLatitude + maxLatitude) / 2, (minLongitude + maxLongitude) / 2]
        }
        out << render(template: '/common/map/exploreMap', model: [center: center, extent: [minLatitude, minLongitude, maxLatitude, maxLongitude], visitorLocation: attrs.visitorLocation == '1', places: places])
    }

}
