package agahisaz

class LikeController {

    def springSecurityService

    def save() {
        def place = Place.get(params.placeId)
        def tip = place?.tips?.find{it.id == params.tipId}
        if(tip){
            if(!tip.likes)
                tip.likes = []
            tip.likes << springSecurityService.currentUser?.id
            place.save(flush:true)
            render 1
        }
        else render 0
    }
}
