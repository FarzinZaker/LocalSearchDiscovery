package agahisaz

import com.pars.agahisaz.User
import utils.SmsHelper

class UserService {
    def mongoService
    def messageSource

    def message(String key, args = null) {
        messageSource.getMessage(key, args?.toArray(), key, null)
    }

    def register(String mobile, String email, String firstName, String lastName, Boolean male) {
        def countRegistered = mongoService.count('user', [$or: [[mobile: mobile ?: '-'], [email: email ?: '-']]])
        if (countRegistered) {
            return [error: message('register.already-registered'), field: mobile ? 'mobile' : 'email']
        }
        def password = (Math.random() * 900000 + 100000) as int
        def user = new User(username: email ?: mobile, password: password, enabled: true)
        user['mobile'] = mobile
        user['email'] = email
        user['firstName'] = firstName
        user['lastName'] = lastName
        user['gender'] = male ? 'male' : 'female'
        user.encodePassword()
        if (!user.save()) {
            return [error: user.errors.allErrors.collect { message(it.code) }, field: mobile ? 'mobile' : 'email']
        }
        if (mobile) {
            SmsHelper.sendSms(mobile, message('register.sms', [password.toString()]))
        }
        return [success: true]
    }

    def findUser(String username) {
        return mongoService.query('user', [$or: [[mobile: username], [email: username], [username: username]]]).find()
    }
}
