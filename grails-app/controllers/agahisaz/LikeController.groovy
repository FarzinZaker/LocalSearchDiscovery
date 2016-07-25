package agahisaz

class LikeController {

    def springSecurityService
    def actionService

    def save() {
        if (!springSecurityService.isLoggedIn()) {
            render 0
            return
        }
        def place = Place.get(params.placeId)
        def tip = place?.tips?.find { it.id == params.tipId }
        if (tip) {
            if (!tip.likes)
                tip.likes = []
            tip.likes << springSecurityService.currentUser?.id
            place.save(flush: true)

            actionService.doAction(Action.LIKE_TIP)

            render 1
        } else render 0
    }
}
