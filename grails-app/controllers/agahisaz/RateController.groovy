package agahisaz

import com.pars.agahisaz.User

class RateController {

    def springSecurityService
    def actionService

    def save() {
        def user = springSecurityService.currentUser as User
        if (!user) {
            render 0;
            return
        }
        def place = Place.get(params.id)
        if (!place) {
            render 0;
            return
        }
        place.rates = (place?.rates?.findAll { rate -> rate.userId != user?.id } ?: []) + [
                userId: user?.id,
                value  : params.value as Integer
        ]
        place.ratesCount = place?.rates?.size()
        place.averageRate = place.rates.sum {rate -> rate.value as Double} / place.ratesCount
        place.save(flush: true)

        actionService.doAction(Action.RATE_PLACE)

        render 1
    }
}
