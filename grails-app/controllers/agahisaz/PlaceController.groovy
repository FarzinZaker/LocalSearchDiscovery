package agahisaz


import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import security.Roles

class PlaceController {

    def springSecurityService

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
        if (place.save(flush: true)) {
            flash.message = message(code: 'place.save.success')
            redirect(action: 'view', id: place.id, params: [name: place.name])
        } else {
            flash.error = place.errors
            redirect(action: 'add')
        }
    }

    def view() {
        def place = Place.get(params.id)
        [place: place]
    }

    def suggestEdit(){
        def place = Place.get(params.id)
        [place: place]
    }

    def saveEditSuggestion() {
        println(params)
        render (params as JSON)
    }
}
