package agahisaz


import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.time.TimeCategory
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
    def delayedMessageService

    @Secured([Roles.AUTHENTICATED])
    def add() {
        [place: [:]]
    }

    def edit() {
        [place: Place.get(params.id)]
    }

    @Secured([Roles.AUTHENTICATED])
    def save() {
        if (params.id) {
            def place = placeService.edit(params)
            if (place) {
                actionService.doAction(Action.EDIT_PLACE)

                flash.message = message(code: 'place.edit.success')
                redirect(action: 'view', id: place.id, params: [name: place.name])
            } else {
                flash.error = place.errors
                redirect(action: 'edit', id: params.id)
            }
        } else {
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

        def cursor = mongoService.getCollection("place").find(query, projection)?.sort(sort)?.limit(200)
        def places = []
        try {
            places = cursor?.findAll()?.each { place -> place.category = CategoryCache.findCategory(place.category) } ?: []
        }
        finally {
            cursor?.close()
        }

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
        if (place.reportType) {
            def user = springSecurityService.currentUser as User
            if (user?.superuserLevel > 0)
                render(view: 'view', model: model)
            else if (place?.creatorId == user?.id) {
                flash.message = message(code: "place.reported.message", args: [message(code: "place.report.type.${place.reportType}")]) +
                        '<br/>' +
                        message(code: 'place.rejected.message.forCreator')
                model.put('showEditButton', true)
                render(view: 'view', model: model)
            } else
                render(view: 'deleted', model: model)
        } else
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

        def result = placeService.search(params)
        request.setAttribute("query", params?.id ?: '')
        request.setAttribute("queryCategory", Category.findByName(params?.id ?: '')?.id ?: 0)
        return result
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
        def cursor = collection.find().skip(skip as Integer)
        def editSuggestion = null
        try {
            editSuggestion = cursor.find()
        }
        finally {
            cursor?.close()
        }
        if (editSuggestion) {
            def es = EditSuggestion.get(editSuggestion._id)
            if (es.hasChange()) {
                editSuggestion.categoryInfo = CategoryCache.findCategory(editSuggestion?.category)
                def place = Place.get(editSuggestion.place)
                place.categoryInfo = CategoryCache.findCategory(place.categoryId)
                return [place: place, editSuggestion: editSuggestion]
            } else {
                es.delete(flush: true)
                editSuggestion = null
                return redirect(action: 'reviewEditSuggestion')
            }
        }
        if (!editSuggestion) {
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

    private static final AtomicInteger reviewSequence = new AtomicInteger(0)

    def review() {
        def place = null
        if (params.id) {
            place = Place.get(params.id)
        } else {
            def collection = mongoService.getCollection('place')
            def max = (collection.count([approved: false]) - 1) as Integer
            def skip = reviewSequence.getAndIncrement()
            if (skip > max) {
                synchronized (reviewSequence) {
                    skip = reviewSequence.get()
                    if (skip > max) {
                        reviewSequence.set(1)
                        skip = 0
                    }
                }
            }

            def cursor = collection.find([approved: false]).skip(skip as Integer)
            try {
                place = cursor.find()
            }
            finally {
                cursor?.close()
            }


            if (place)
                place = Place.get(place._id)
        }

        if (place) {
            def cursor = mongoService.getCollection("place").find([
                    $text     : [$search: place?.name?.split(' ')?.findAll {
                        it.size() > 2
                    }?.join(' ')],
                    category  : place.category?.id,
                    _id       : [$ne: place?.id],
                    approved  : true,
                    reportType: null
            ])?.limit(10)
            def similarPlacesInName = []
            try {
                similarPlacesInName = cursor?.findAll()?.each { p -> p.category = CategoryCache.findCategory(p.category) } ?: []
            }
            finally {
                cursor?.close()
            }

            def similarPlaces = placeService.similarPlacesGlobal(place, 20)

            def categoriesMap = [:]
            similarPlaces*.category.each {
                if (categoriesMap.containsKey(it))
                    categoriesMap[it]++
                else
                    categoriesMap.put(it, 1)
            }

            [
                    place                : place,
                    otherPlacesOfThisUser: Place.findAllByCreatorAndApprovedAndReportTypeIsNull(place?.creator, true, [max: 100]),
                    similarPlaces        : similarPlacesInName,
                    suggestedCategories  : categoriesMap
            ]
        } else
            render view: '/place/reviewEnded'
    }

    def saveReview() {
        if (params.btn == message(code: 'place.review.next'))
            return redirect(action: 'review')

        def place = Place.get(params.placeId)
        place.approved = true
        if (params.reason != 'nothing')
            place.reportType = params.reason
        else
            place.reportType = null
        def additionalInfo = params.additionalInfo?.trim()
        if (additionalInfo && additionalInfo != '')
            place.reportComment = additionalInfo
        else
            place.reportComment = null
        place.save(flush: true)

        if (place.reportType) {
            def sendDate = new Date()
            use(TimeCategory) {
                sendDate = sendDate + 2.hours
            }
            delayedMessageService.schedule(place?.creator, message(code: 'place.review.reject.message.title')?.toString(), message(code: 'place.review.reject.message.body')?.toString(), 'place.review.reject', sendDate)
        }

        if (params.btn == message(code: 'place.review.saveAndNext'))
            return redirect(action: 'review')
        else
            return redirect(action: 'view', id: params.placeId)
    }

    def changeCategory() {
        try {
            def place = Place.get(params.id)
            place.category = Category.get(params.category)
            place.save(flush: true)
        }
        finally {
            redirect(action: 'review', id: params.id)
        }
    }
}
