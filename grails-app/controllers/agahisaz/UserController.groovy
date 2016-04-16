package agahisaz

import com.pars.agahisaz.User
import grails.converters.JSON

class UserController {
    def userService
    def springSecurityService

    def register() {
        if (!params.captcha || !params.captcha.isNumber() || session['registerCaptcha'] != params.captcha as int)
            return render([error: message(code: 'invalid.captcha'), field: 'captcha'] as JSON)
        def res = userService.register(params.mobile, params.email, params.firstName, params.lastName, params.male ? true : false)
        if (res.error) {
            return render(res as JSON)
        }
        render([success: true, message: message(code: 'register.success')] as JSON)
    }

    def forgetPassword() {

    }

    def resetPassword() {
        if (!params.captcha || !params.captcha.isNumber() || session['forgetPasswordCaptcha'] != params.captcha as int) {
            flash.error = message(code: 'invalid.captcha')
            return redirect(action: 'forgetPassword')
        }
        flash.error = userService.resetPassword(params.username)
        if (!flash.error) {
            flash.info = message(code: 'user.resetPassword.done')
            redirect(controller: 'login', action: 'auth')
        } else
            redirect(action: 'forgetPassword')
    }

    def mustChangePassword() {
        flash.info = message(code: 'user.mustChangePassword')
        render view: 'changePassword', model: [skipOldPassword: true]
    }

    def changePassword() {

    }

    def saveNewPassword() {
        def result = userService.changePassword(params.oldPassword, params.newPassword)
        if (!result)
            redirect(uri: '/')
        else {
            flash.message = result.error
            redirect(action: result.action)
        }
    }

    def profile() {
        render view: 'settings', model: [
                currentTab: 'profile',
                user      : springSecurityService.currentUser as User
        ]
    }

    def saveBasicInfo() {
        def res = userService.updateBasicInfo(params.profileImage, params.mobile, params.email, params.firstName, params.lastName, params.male ? true : false)
        if (res.error) {
            flash.error = res.error
        }
        redirect(action: 'profile')

    }
}
