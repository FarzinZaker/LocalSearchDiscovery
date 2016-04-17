package agahisaz

class MapTagLib {

    static namespace = "map"

    def locationPicker = { attrs, body ->
        out << render(template: '/common/map/locationPicker', model: [name: attrs.name, id: attrs.id ?: attrs.name, cssClass: attrs.cssClass, style: attrs.style, center: attrs.center, visitorLocation: attrs.visitorLocation == '1', width: attrs.width, height: attrs.height])
    }

}
