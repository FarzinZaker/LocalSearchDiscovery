package agahisaz

import com.pars.agahisaz.User

class TipController {

    def springSecurityService
    def imageService
    def actionService

    def save() {
//        println(params?.id)
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
        place.tips = place.tips ?: []
        def tip = [
                id      : UUID.randomUUID()?.toString(),
                userId  : user?.id,
                fullName: "${user?.firstName} ${user.lastName}"?.toString(),
                body    : params.body?.trim(),
                date    : new Date()
        ]
        place.tips << tip
        place.save(flush: true)

        def imageList = []
        if (params.image) {
            if (params.image instanceof String)
                imageList = [params.image]
            else
                imageList = params.image
        }
        imageList.each { String image ->
            imageService.saveImage(image, 'tip', tip.id)
        }

        actionService.doAction(Action.ADD_TIP)

        render(template: '/place/tip/item', model: [
                placeId  : place?.id,
                tip      : tip,
                image    : Image.findByTypeAndOwnerIdAndSize('tip', tip?.id, 100),
                slideDown: true])
    }
}
