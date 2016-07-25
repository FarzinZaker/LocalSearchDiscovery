package agahisaz

class ReportController {

    def springSecurityService

    def save() {
        if (!springSecurityService.isLoggedIn()) {
            render 0
            return
        }
        def place = Place.get(params.placeId)
        def tip = place?.tips?.find { it.id == params.tipId }
        if (tip) {
            if (!tip.reports)
                tip.reports = []
            tip.reports << springSecurityService.currentUser?.id
            if (!place.reportedTips)
                place.reportedTips = []
            if (!place.reportedTips.contains(tip.id))
                place.reportedTips << tip.id
            place.save(flush: true)
            render 1
        } else render 0
    }
}
