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
        def queryCategory = request.getAttribute('queryCategory')?.toString()
        def queryIcon = createLink(controller: 'image', action: 'category', id: queryCategory)
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
        out << render(template: '/place/aggregateRatingFlag', model: [rate: place?.averageRate, description: description, cssClass: attrs.cssClass])
    }

    def addTip = { attrs, body ->
        def user = springSecurityService.currentUser as User
        if (user)
            out << render(template: '/place/tip/add', model: [authorName: "${user?.firstName} ${user?.lastName}"])
    }

    def tipList = { attrs, body ->
        def place = attrs.place as Place
        def tipList = place.tips?.sort { -(it.date?.time ?: 0) }
        out << render(template: '/place/tip/listHeader', model: [tipsCount: tipList?.size() ?: 0])
        def userId = springSecurityService.currentUser?.id
        out << "<div class='tipItems'>"
        if (tipList?.size())
            tipList?.each {
                out << render(template: '/place/tip/item', model: [
                        placeId        : place?.id,
                        tip            : it,
                        alreadyLiked   : it.likes?.contains(userId),
                        alreadyReported: it.reports?.contains(userId),
                        image          : Image.findByTypeAndOwnerIdAndSize('tip', it?.id, 100),
                        lazyLoadImages : true])
            }
        else
            out << render(template: '/place/tip/empty')
        out << "</div>"
    }

    def exploreItem = { attrs, body ->
        def place = attrs.place
        def tip = place?.tips?.last()?.body
        out << render(template: '/place/explore/item', model: [place: place, index: attrs.index, tip: tip])
    }

    def similarItem = { attrs, body ->
        def place = attrs.place
        out << render(template: '/place/item', model: [place: place])
    }
}
