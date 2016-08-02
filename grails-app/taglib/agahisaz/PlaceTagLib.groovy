package agahisaz

import cache.CategoryCache
import cache.VariableCache
import com.pars.agahisaz.User

class PlaceTagLib {

    def springSecurityService
    def mongoService

    static namespace = "place"

    def waitingEditSuggestions = { attrs, body ->
        if ((springSecurityService.currentUser as User).superuserLevel > 0) {
            def placesCount = EditSuggestion.count()
            if (placesCount > 0)
                out << render(template: '/common/place/waitingEditSuggestions', model: [editSuggestionsCount: placesCount])
        }
    }

    def waitingPlaces = { attrs, body ->
        if ((springSecurityService.currentUser as User).superuserLevel > 0) {
            def editSuggestionsCount = mongoService.getCollection('place').count([approved: false])
            if (editSuggestionsCount > 0)
                out << render(template: '/common/place/waitingPlaces', model: [placesCount: editSuggestionsCount])
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
        def tipList = place.tips?.findAll { !it.removed }?.sort { -(it.date?.time ?: 0) }
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

    def addTagToSearchLink = { attrs, body ->
        def tags = params.tags?.split('[|]')?.toList() ?: []
        tags.add(attrs.tag)
        def paramList = params.clone()
        paramList.remove('tags')
        paramList += [tags: tags?.join('|')]
        out << createLink(controller: 'place', action: 'explore', params: paramList)
    }

    def removeTagToSearchLink = { attrs, body ->
        def tags = params.tags?.split('[|]')?.toList() ?: []
        tags.remove(attrs.tag)
        def paramList = params.clone()
        paramList.remove('tags')
        if (tags?.size())
            paramList += [tags: tags?.join('|')]
        out << createLink(controller: 'place', action: 'explore', params: paramList)
    }

    def topCategories = { attrs, body ->
        out << "<div class='topCategories row'>"
        Category.findAllByParentIsNull([max: 6])?.each { category ->
            out << """<div class="col-lg-2 col-md-2 col-sm-2 col-xs-6">
                <a href='${createLink(controller: 'place', action: 'explore', id: category?.name)}'>
                    <img src='${
                createLink(controller: 'image', action: 'category', params: [id: category.id, size: attrs.iconSize])
            }'/>
                    <span class='text'>${category?.name}</span>
                    <span class='clearfix'></span>
                </a>
            </div>"""
        }
        out << "</div>"
    }

    def topPlaces = { attrs, body ->
        def places = mongoService.getCollection('place').aggregate(
                [$sort: [averageRate: -1, ratesCount: -1]],
                [$limit: 20]
        ).results()
        places.each {
            it.category = CategoryCache.findCategory(it.category)
            out << render(template: '/place/placeCard', model: [place: it])
        }
    }

    def topCities = { attrs, body ->
        def cities = findTopCities()
        out << render(template: '/layouts/common/cityGrid', model: [cities: cities])
    }

    private def findTopCities() {
        if (!VariableCache.topCitiesCache) {
            VariableCache.topCitiesCache = mongoService.getCollection('place').aggregate(
                    [$group: [_id: '$city', province: [$first: '$province'], count: [$sum: 1]]],
                    [$sort: ['count': -1]],
                    [$limit: 12]
            )?.results()
        }
        VariableCache.topCitiesCache
    }
}
