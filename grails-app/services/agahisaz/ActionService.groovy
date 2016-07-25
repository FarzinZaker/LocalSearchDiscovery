package agahisaz

import com.pars.agahisaz.User
import org.springframework.web.context.request.RequestContextHolder as RCH


class ActionService {

    def userAgentIdentService
    def springSecurityService
    def mongoService

    def doAction(String actionName, User user = null) {
        applyAction(createActionInstance(actionName, user))
    }

    private void applyAction(ActionInstance actionInstance) {
        if (actionInstance) {
//            Thread.start {
            actionInstance?.save()
            def score = actionInstance?.action?.grantScore
            if (score != 0)
                mongoService.getCollection('user').update(
                        [_id: actionInstance?.userId],
                        [$inc: [
                                'totalScore': score,
                                'weekScore' : score
                        ]]
                )
//            }
        }
    }

    private ActionInstance createActionInstance(String actionName, User user) {

        def action = Action.findByName(actionName)
        if (!action)
            return null

        def actionInstance = new ActionInstance()

        def request = RCH.currentRequestAttributes().currentRequest
        def params = RCH.requestAttributes.params as Map

        actionInstance.action = action
        actionInstance.date = new Date()
        actionInstance.user = user ?: springSecurityService.currentUser as User
        actionInstance.appController = params.controller
        actionInstance.appAction = params.action
        params.remove('controller')
        params.remove('action')
        actionInstance.params = params
        if (!user) {
            actionInstance.ipAddress = request.getHeader("X-Forwarded-For") ?: request.getHeader("Client-IP") ?: request.getRemoteAddr()
            actionInstance.browserData = [
                    operatingSystem: userAgentIdentService.getOperatingSystem(),
                    browserName    : userAgentIdentService.getBrowserName(),
                    browserVersion : userAgentIdentService.getBrowserVersion()
            ]
        }
        actionInstance
    }

}
