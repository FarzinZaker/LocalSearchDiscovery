package agahisaz


import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import security.Roles
import cache.CategoryCache

class PlaceController {

    def springSecurityService
    def mongoService
    def imageService

    @Secured([Roles.AUTHENTICATED])
    def add() {}

    @Secured([Roles.AUTHENTICATED])
    def save() {
        def place = new Place()
        place.name = params.name.trim()
        place.province = params.province
        place.city = params.city
        place.address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
        place.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        place.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        place.location = params.location.toString().split(',').collect { it.toDouble() }
        place.category = Category.findByName(params.category3?.toString() ?: params.category2?.toString())
        place.creator = springSecurityService.currentUser as User
        place.tags = new ArrayList()
        params.tags?.trim()?.split(',')?.each { String tagName ->
            place.tags.add(tagName.trim())
        }
        if (place.save(flush: true)) {
            flash.message = message(code: 'place.save.success')
            redirect(action: 'view', id: place.id, params: [name: place.name])
        } else {
            flash.error = place.errors
            redirect(action: 'add')
        }
    }

    def importJson() {
        def cat = Category.findByName(params.category?.trim()?.toString())
        def address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
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
        params.tags?.trim()?.split(',')?.each { String tagName ->
            place.tags.add(tagName.trim())
        }

        if (place.save(flush: true)) {
            if (params.logo) {
                imageService.saveImage(params.logo, 'placeLogo', place.id.toString())
            }
            render 1
        } else {
            render(place.errors as JSON)
        }
    }

    def view() {
        def place = Place.get(params.id)
        [place: place]
    }

    def suggestEdit() {
        def place = Place.get(params.id)
        def editSuggestion = EditSuggestion.findByPlace(place)
        [place: editSuggestion ?: place, placeId: place?.id]
    }

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
        if (editSuggestion.save(flush: true)) {
            render 1
        } else {
            render(editSuggestion.errors as JSON)
        }
    }

    def explore() {
        def sort = [:]
        def query = [:]
        def projection = null
        if (params.province && params.city)
            query << [province: params.province]
        if (params.city)
            query << [city: params.city]

        def queryString = params.id?.toString()?.trim() ?: ''
        request.setAttribute("query", queryString)
        def category = Category.findByName(queryString)
        if (category) {
            request.setAttribute("queryIcon", category.iconPath)
            queryString = ''
            query << [category: [$in: CategoryCache.findCategory(category.id).childIdList + [category.id]]]
        }
        if (queryString != '') {
            request.setAttribute("queryIcon", "no-image.png")
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
        } else {
            //search using geoNear
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
                }
            }
        }
        [places: mongoService.query("place", query, projection)?.sort(sort)?.limit(50)?.findAll()?.each { place -> place.category = Category.get(place.category) } ?: []]
    }
}
