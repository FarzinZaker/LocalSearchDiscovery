package agahisaz

import com.pars.agahisaz.User

class PlaceTagLib {

    def springSecurityService

    static namespace = "place"

    def waitingEditSuggestions = { attrs, body ->
        if ((springSecurityService.currentUser as User).superuserLevel > 0) {
            def editSuggestionsCount = EditSuggestion.count()
            if (editSuggestionsCount > 0)
                out << render(template: '/common/place/waitingEditSuggestions', model: [editSuggestionsCount: editSuggestionsCount])
        }
    }

    def searchBox = { attrs, body ->
        def query = request.getAttribute('query')?.toString()
        def queryIconPath = request.getAttribute('queryIcon')?.toString()
        def queryIcon
        if (queryIconPath?.contains('/'))
            queryIcon = resource(dir: 'images/categories/' + queryIconPath?.split('/')?.first(), file: queryIconPath?.split('/')?.last() + '32.png')
        else
            queryIcon = resource(dir: 'images/categories/', file: 'no-image.png')
        out << render(template: '/layouts/common/searchBox', model: [province: params.province, city: params.city, query: query, queryIcon: queryIcon])
    }
}
