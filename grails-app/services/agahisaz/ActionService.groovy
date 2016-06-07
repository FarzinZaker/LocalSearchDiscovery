package agahisaz

import com.pars.agahisaz.User
import org.springframework.web.context.request.RequestContextHolder as RCH


class ActionService {

    def userAgentIdentService
    def springSecurityService
    def mongoService

    def doAction(String actionName) {
        applyAction(createActionInstance(actionName))
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

    private ActionInstance createActionInstance(String actionName) {
        if (!springSecurityService.loggedIn)
            return null

        def action = Action.findByName(actionName)
        if (!action)
            return null

        def actionInstance = new ActionInstance()

        def request = RCH.currentRequestAttributes().currentRequest
        def params = RCH.requestAttributes.params as Map

        actionInstance.action = action
        actionInstance.date = new Date()
        actionInstance.user = springSecurityService.currentUser as User
        actionInstance.ipAddress = request.getHeader("X-Forwarded-For") ?: request.getHeader("Client-IP") ?: request.getRemoteAddr()
        actionInstance.appController = params.controller
        actionInstance.appAction = params.action
        params.remove('controller')
        params.remove('action')
        actionInstance.params = params
        actionInstance.browserData = [
                operatingSystem: userAgentIdentService.getOperatingSystem(),
                browserName    : userAgentIdentService.getBrowserName(),
                browserVersion : userAgentIdentService.getBrowserVersion()
        ]
        actionInstance
    }

}
