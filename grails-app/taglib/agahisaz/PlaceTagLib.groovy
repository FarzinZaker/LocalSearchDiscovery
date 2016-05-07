package agahisaz

import com.mongodb.DBObject
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

    def rate = { attrs, body ->
        def user = springSecurityService.currentUser as User
        if (user) {
            def place = attrs.place as Place
            def rate = place?.rates?.find { it?.userId == user?.id }?.toMap()
            out << render(template: '/place/rate', model: [placeId: place?.id, currentRate: rate?.value])
        }
    }

    def aggregateRating = { attrs, body ->
        def place = attrs.place as Place
        def description = null
        if (place?.averageRate != null) {
            if (place?.averageRate <= 3)
                description = 'dislike'
            else if (place?.averageRate >= 7)
                description = 'like'
            else
                description = 'mixed'
        }
        out << render(template: '/place/aggregateRating', model: [rate: place?.averageRate, votesCout: place?.ratesCount, description: description])
    }

    def aggregateRatingFlag = { attrs, body ->
        def place = attrs.place
        def description = null
        if (place?.averageRate != null) {
            if (place?.averageRate <= 3)
                description = 'dislike'
            else if (place?.averageRate >= 7)
                description = 'like'
            else
                description = 'mixed'
        }
        out << render(template: '/place/aggregateRatingFlag', model: [rate: place?.averageRate, description: description])
    }

    def addTip = { attrs, body ->
        def user = springSecurityService.currentUser as User
        if (user)
            out << render(template: '/place/tip/add', model: [authorName: "${user?.firstName} ${user?.lastName}"])
    }
}
