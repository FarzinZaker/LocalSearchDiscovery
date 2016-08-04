package agahisaz

import com.pars.agahisaz.User

class SecurityTagLib {

    def springSecurityService
    def userService

    static namespace = "security"

    def userFullName = {attrs, body ->
        def user = springSecurityService.currentUser as User
        out << user.firstName + " " + user.lastName
    }

    def userId = {attrs, body ->
        def user = springSecurityService.currentUser as User
        out << user?.id
    }

}
