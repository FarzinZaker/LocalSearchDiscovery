package agahisaz

import com.pars.agahisaz.User
import grails.converters.JSON
import utils.CaptchaHelper

class UserController {
    def userService

    def register() {
        if (session['captcha'] != params.captcha as int)
            return render([error: message(code: 'invalid.captcha'), field: 'captcha'] as JSON)
        def res = userService.register(params.mobile, params.email, params.firstName, params.lastName, params.male ? true : false)
        if (res.error) {
            return render(res as JSON)
        }
        render([success: true, message: message(code: 'register.success')] as JSON)
    }
}
