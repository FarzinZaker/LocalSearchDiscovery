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

}
