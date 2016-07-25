package agahisaz

import com.pars.agahisaz.User

class ActionInstance {
    static mapWith = "mongo"

    Action action
    Date date
    User user
    String ipAddress
    String appController
    String appAction
    Map params
    Map browserData

    static constraints = {
        appController nullable: true
        appAction nullable: true
        ipAddress nullable: true
        browserData nullable: true
    }
}
