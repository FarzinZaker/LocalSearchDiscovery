package agahisaz


import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import security.Roles

class PlaceController {

    def springSecurityService
    def mongoService

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
        def place = new Place()
        place.name = params.name?.trim()
        place.province = params.province
        place.city = params.city
        place.address = params.address && params.address?.trim() != '' ? params.address?.trim() : null
        place.phone = params.phone && params.phone?.trim() != '' ? params.phone?.trim() : null
        place.postalCode = params.postalCode && params.postalCode?.trim() != '' ? params.postalCode?.trim() : null
        place.location = params.location?.toString()?.split(',')?.collect { it.toDouble() }
        place.category = Category.findByName(params.category?.trim()?.toString())
        place.creator = User.list(max: 0)?.find()
        place.tags = new ArrayList()
        params.tags?.trim()?.split(',')?.each { String tagName ->
            place.tags.add(tagName.trim())
        }
        if (place.save(flush: true)) {
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
        [places: mongoService.query("place", [$text: [$search: params.id]]).limit(50).findAll().each { place -> place.category = Category.get(place.category) }]
    }
}
