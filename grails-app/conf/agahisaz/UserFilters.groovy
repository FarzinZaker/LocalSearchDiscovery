package agahisaz

import com.pars.agahisaz.User

class UserFilters {

    def springSecurityService

    def filters = {

        all(controller: '*', action: '*') {
            before = {
                if (controllerName != 'logout' && !['mustChangePassword', 'changePassword', 'saveNewPassword'].contains(actionName))
                    if (springSecurityService.isLoggedIn()) {
                        def user = springSecurityService.currentUser as User
                        if (!user['changedPassword']) {
                            return redirect(controller: 'user', action: 'mustChangePassword')
                        }
                    }
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
