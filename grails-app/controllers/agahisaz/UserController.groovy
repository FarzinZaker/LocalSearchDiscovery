package agahisaz

import cache.CategoryCache
import com.pars.agahisaz.User
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import security.Roles

class UserController {
    def userService
    def springSecurityService
    def mongoService

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

    @Secured([Roles.AUTHENTICATED])
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

    @Secured([Roles.AUTHENTICATED])
    def profile() {
        render view: 'settings', model: [
                currentTab: 'profile',
                user      : springSecurityService.currentUser as User
        ]
    }

    @Secured([Roles.AUTHENTICATED])
    def saveBasicInfo() {
        def res = userService.updateBasicInfo(params.profileImage, params.mobile, params.email, params.firstName, params.lastName, params.male ? true : false, params.province, params.city, params.bio)
        if (res.error) {
            flash.error = res.error
        }
        redirect(action: 'profile')

    }

    def info() {
        def user
        if (params.id)
            user = User.get(params.id)
        else
            user = springSecurityService.currentUser as User
        def tips = mongoService.getCollection('place').aggregate(
                [$match: ['tips.userId': user?.id]],
                [$unwind: '$tips'],
                [$match: ['tips.userId': user?.id]]
        ).results().each {
            it.category = CategoryCache.findCategory(it.category)
        }
        [user: user, tips: tips]
    }

    @Secured([Roles.AUTHENTICATED])
    def mySuggests() {
        [
                rejectedPlaces: Place.findAllByCreatorAndApprovedAndReportTypeIsNotNull(springSecurityService.currentUser as User, true),
                waitingPlaces : Place.findAllByCreatorAndApproved(springSecurityService.currentUser as User, false),
                approvedPlaces: Place.findAllByCreatorAndApprovedAndReportTypeIsNull(springSecurityService.currentUser as User, true)
        ]
    }

    def setUserLocation() {
        def nearParams = params.id?.toString()?.split(',')
        if (nearParams.size() == 2)
            session['location'] = [lat: nearParams[0]?.toDouble(), lon: nearParams[1]?.toDouble()]
        render ''
    }
}
