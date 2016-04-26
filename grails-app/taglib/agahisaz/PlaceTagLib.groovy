package agahisaz

import com.pars.agahisaz.User

class PlaceTagLib {

    def springSecurityService

    static namespace = "place"

    def waitingEditSuggestions = {attrs, body ->
        if((springSecurityService.currentUser as User).superuserLevel > 0) {
            def editSuggestionsCount = EditSuggestion.count()
            if (editSuggestionsCount > 0)
                out << render(template: '/common/place/waitingEditSuggestions', model: [editSuggestionsCount: editSuggestionsCount])
        }
    }
}
