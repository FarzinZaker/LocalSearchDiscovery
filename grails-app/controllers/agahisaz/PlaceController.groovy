package agahisaz


import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import security.Roles
import cache.CategoryCache
import sun.misc.BASE64Decoder

import java.util.concurrent.atomic.AtomicInteger

class PlaceController {

    def springSecurityService
    def mongoService
    def imageService
    def placeService
    def actionService

    @Secured([Roles.AUTHENTICATED])
    def add() {}

    @Secured([Roles.AUTHENTICATED])
    def save() {
        def place = placeService.save(params)
        if (place) {
            actionService.doAction(Action.ADD_PLACE)

            flash.message = message(code: 'place.save.success')
            redirect(action: 'view', id: place.id, params: [name: place.name])
        } else {
            flash.error = place.errors
            redirect(action: 'add')
        }
    }
    static def cur = System.currentTimeMillis()
    static def count = 0
    def searchImportJSON() {
        def sort = [:]
        def query = [:]
        def aggregateQuery
        def tags
        if (params.tags)
            tags = params.tags?.split('[|]')?.toList() ?: []
        def projection = null
        if (params.province && params.city)
            query << [province: params.province]
        if (params.city)
            query << [city: params.city]

        def queryString = params.id?.toString()?.trim() ?: ''
        request.setAttribute("query", queryString)
        def category = Category.findByName(queryString)
        if (category) {
            request.setAttribute("queryCategory", category?.id)
            queryString = ''
            query << [category: [$in: CategoryCache.findCategory(category.id)?.childIdList ?: [] + [category.id]]]
        }
        if (tags)
            query << [tags: [$all: tags]]

        if (queryString != '') {
            request.setAttribute("queryCategory", "0")
            //search using geoWithin
            query << [$text: [$search: params.id]]
            projection = [score: [$meta: "textScore"]]
            sort << ['score': ['$meta': 'textScore']]
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
            aggregateQuery = query.clone()
        } else {
            //search using geoNear
            aggregateQuery = query.clone()
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [
                            $near       : [latitude, longitude],
                            $maxDistance: 0.03D
                    ]
                    ]
                    aggregateQuery << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
        }

        def places = mongoService.getCollection("place").find(query, projection)?.sort(sort)?.limit(200)?.findAll()?.each { place -> place.category = CategoryCache.findCategory(place.category) } ?: []

        render(places as JSON)
    }
    def importJson() {
        if (count < 0)
            count = 0
        count++

        def cc = System.currentTimeMillis()
        def cat = Category.findByName(params.category?.trim()?.toString())
        def address = params.address ? new String(new BASE64Decoder().decodeBuffer(params.address?.trim()), 'UTF8')?.trim() : null
        def city = params.city
        def name = params.name?.trim()
        def place = Place.findByNameAndCityAndCategoryAndAddress(name, city, cat, address) ?: new Place()
        place.name = name
        place.province = params.province
        place.city = city
        place.address = address
        place.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        place.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        place.location = params.location?.toString()?.split(',')?.collect { it.toDouble() }
        place.category = cat
        place.creator = User.list(max: 0)?.find()
        place.tags = new ArrayList()
        new String(new BASE64Decoder().decodeBuffer(params.tags), 'UTF8')?.trim()?.split(',')?.each { String tagName ->
            place.tags.add(tagName.trim())
            Tag.findByName(tagName.trim()) ?: new Tag(name: tagName.trim()).save(flush: true)
        }

        if (place.save(flush: true)) {
            if (params.logo) {
                imageService.saveImage(params.logo, 'placeLogo', place.id.toString())
            }
            render 1
        } else {
            render(place.errors as JSON)
        }
//        println((System.currentTimeMillis() - cur) + ' ' + count + ' ' + (System.currentTimeMillis() - cc))
        cur = System.currentTimeMillis()
        count--
    }

    def view() {
        def place = Place.get(params.id)
        def model = [place: place, similarPlaces: placeService.similarPlaces(place)]
        if (place.reportType)
            render(view: 'deleted', model: model)
        else
            render(view: 'view', model: model)
    }

    @Secured([Roles.AUTHENTICATED])
    def suggestEdit() {
        def place = Place.get(params.id)
        def editSuggestion = EditSuggestion.findByPlace(place)
        [place: editSuggestion ?: place, placeId: place?.id]
    }

    @Secured([Roles.AUTHENTICATED])
    def saveEditSuggestion() {

        def place = Place.get(params.placeId as Long)
        EditSuggestion.findAllByPlace(place).each {
            it.delete(flush: true)
        }

        def editSuggestion = new EditSuggestion()
        editSuggestion.place = place
        editSuggestion.name = params.name.trim()
        editSuggestion.province = params.province
        editSuggestion.city = params.city
        editSuggestion.address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
        editSuggestion.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        editSuggestion.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        editSuggestion.location = params.location.toString().split(',').collect { it.toDouble() }
        editSuggestion.category = Category.findByName(params.category3?.toString() ?: params.category2?.toString())
        editSuggestion.creator = springSecurityService.currentUser as User
        editSuggestion.tags = new ArrayList()
        params.tags?.trim()?.split(',')?.each { String tagName ->
            editSuggestion.tags.add(tagName.trim())
        }
        if (params.reportReason) {
            editSuggestion.reportType = params.reportReason
            editSuggestion.reportComment = params.additionalInfo
        }
        if (editSuggestion.save(flush: true)) {
            render 1
        } else {
            render(editSuggestion.errors as JSON)
        }
    }

    def explore() {
        def sort = [:]
        def query = [:]
        def aggregateQuery
        def tags
        if (params.tags)
            tags = params.tags?.split('[|]')?.toList() ?: []
        def projection = null
        if (params.province && params.city)
            query << [province: params.province]
        if (params.city)
            query << [city: params.city]

        def queryString = params.id?.toString()?.replace('-', ' ')?.trim() ?: ''
        request.setAttribute("query", queryString)
        def category = Category.findByName(queryString)
        if (category) {
            request.setAttribute("queryCategory", category?.id)
            queryString = ''
            query << [category: [$in: CategoryCache.findCategory(category.id)?.childIdList ?: [] + [category.id]]]
        }
        if (tags)
            query << [tags: [$all: tags]]

        if (queryString != '') {
            request.setAttribute("queryCategory", "0")
            //search using geoWithin
            query << [$text: [$search: params.id]]
            projection = [score: [$meta: "textScore"]]
            sort << ['score': ['$meta': 'textScore']]
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
            aggregateQuery = query.clone()
        } else {
            //search using geoNear
            aggregateQuery = query.clone()
            if (params.near) {
                def nearParams = params.near?.toString()?.split(',')
                if (nearParams.size() == 2) {
                    def latitude = nearParams[0]?.toDouble()
                    def longitude = nearParams[1]?.toDouble()
                    query << [location: [
                            $near       : [latitude, longitude],
                            $maxDistance: 0.03D
                    ]
                    ]
                    aggregateQuery << [location: [$geoWithin: [$center: [[latitude, longitude], (params.radius?.toDouble()) ?: 0.03D]]]]
                }
            }
        }

        def places = mongoService.getCollection("place").find(query, projection)?.sort(sort)?.limit(50)?.findAll()?.each { place -> place.category = CategoryCache.findCategory(place.category) } ?: []
        def newTags = []
        if (places?.size()) {
            if (tags?.size())
                newTags = mongoService.getCollection("place").aggregate([$match: aggregateQuery], [$unwind: '$tags'], [$match: [tags: [$nin: tags]]], [$group: [_id: '$tags', count: [$sum: 1]]], [$sort: ['count': -1]], [$limit: 10])?.results()
            else
                newTags = mongoService.getCollection("place").aggregate([$match: aggregateQuery], [$unwind: '$tags'], [$group: [_id: '$tags', count: [$sum: 1]]], [$sort: ['count': -1]], [$limit: 10])?.results()
        }
        [places: places, tags: newTags, currentTags: tags]
    }


    private static final AtomicInteger editSuggestionSequence = new AtomicInteger(0)

    def reviewEditSuggestion() {
        def collection = mongoService.getCollection('editSuggestion')
        def max = (collection.count() - 1) as Integer
        def skip = editSuggestionSequence.getAndIncrement()
        if (skip > max) {
            synchronized (editSuggestionSequence) {
                skip = editSuggestionSequence.get()
                if (skip > max) {
                    editSuggestionSequence.set(1)
                    skip = 0
                }
            }
        }
        def editSuggestion = collection.find().skip(skip as Integer).find()
        if (editSuggestion) {
            editSuggestion.categoryInfo = CategoryCache.findCategory(editSuggestion?.category)
            def place = Place.get(editSuggestion.place)
            place.categoryInfo = CategoryCache.findCategory(place.categoryId)
            [place: place, editSuggestion: editSuggestion]
        } else {
            def reportedPlace = mongoService.getCollection('place').aggregate(
                    [$match: ['reportedTips': [$ne: null, $not: [$size: 0]]]],
                    [$unwind: '$tips'],
                    [$match: ['tips.reports': [$ne: null]]]
            ).results()?.find()
            if (reportedPlace) {
                render view: '/place/reviewTipReport', model: [place: reportedPlace, users: User.findAllByIdInList(reportedPlace?.tips?.reports ?: [])]
            } else
                render view: '/place/reviewEditSuggestionEnded'
        }

    }

    @Secured([Roles.AUTHENTICATED])
    def acceptEdit() {

        def editSuggestion = EditSuggestion.get(params.editSuggestion)
        def place = Place.get(params.place)
        place."${params.field}" = editSuggestion."${params.field}"
        if (params.field == 'city')
            place.province = editSuggestion.province
        place.save(flush: true)

        def next = 0
        if (!editSuggestion.hasChange(params.field)) {
            next = 1
            editSuggestion.delete()
        }
        actionService.doAction(Action.ACCEPT_EDIT)
        actionService.doAction(Action.EDIT_ACCEPTED, editSuggestion.creator)
        render([
                message: g.render(template: '/common/score', model: [score: 3]),
                next   : next
        ] as JSON)
    }

    @Secured([Roles.AUTHENTICATED])
    def rejectEdit() {

        def editSuggestion = EditSuggestion.get(params.editSuggestion)
        def place = Place.get(params.place)
        editSuggestion."${params.field}" = place."${params.field}"
        if (params.field == 'city')
            editSuggestion.province = place.province
        place.save(flush: true)

        def next = 0
        if (!editSuggestion.hasChange(params.field)) {
            next = 1
            editSuggestion.delete()
        }
        actionService.doAction(Action.REJECT_EDIT)
        actionService.doAction(Action.EDIT_REJECTED, editSuggestion.creator)
        render([
                message: g.render(template: '/common/score', model: [score: 3]),
                next   : next
        ] as JSON)
    }

    @Secured([Roles.AUTHENTICATED])
    def acceptTipReport() {
        def place = Place.get(params.id)
        def userIds = []
        place.tips.each { tip ->
            if (tip.id == params.tip) {
                tip.removed = true
                userIds = tip.reports
                tip.reports = null
                place.reportedTips.remove(tip.id)
            }
        }
        place.save(flush: true)

        actionService.doAction(Action.ACCEPT_TIP_REPORT)
        userIds.each { userId ->
            actionService.doAction(Action.TIP_REPORT_ACCEPTED, User.get(userId))
        }

        redirect(action: 'reviewEditSuggestion')
    }

    @Secured([Roles.AUTHENTICATED])
    def rejectTipReport() {
        def place = Place.get(params.id)
        def userIds = []
        place.tips.each { tip ->
            if (tip.id == params.tip) {
                tip.removed = false
                userIds = tip.reports
                tip.reports = null
                place.reportedTips.remove(tip.id)
            }
        }
        place.save(flush: true)

        actionService.doAction(Action.REJECT_TIP_REPORT)
        userIds.each { userId ->
            actionService.doAction(Action.TIP_REPORT_REJECTED, User.get(userId))
        }

        redirect(action: 'reviewEditSuggestion')
    }
}
