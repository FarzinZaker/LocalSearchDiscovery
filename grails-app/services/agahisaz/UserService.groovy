package agahisaz

import agahisaz.old.OldAdvertisement
import agahisaz.old.OldUser
import com.pars.agahisaz.User
import org.springframework.beans.factory.InitializingBean
import utils.SmsHelper

class UserService implements InitializingBean {
    def mongoService
    def messageSource
    def springSecurityService
    def mailService
    def imageService
    def gspTagLibraryLookup  // being automatically injected by spring
    def g

    def message(String key, args = null) {
        messageSource.getMessage(key, args?.toArray(), key, null)
    }

    @Override
    void afterPropertiesSet() throws Exception {

        g = gspTagLibraryLookup.lookupNamespaceDispatcher("g")
        assert g
    }

    def register(String mobile, String email, String firstName, String lastName, Boolean male, Boolean oldUser = false) {
        def countRegistered = mongoService.getCollection('user').count([$or: [[mobile: mobile ?: '-'], [email: email ?: '-']]])
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
        user['changedPassword'] = false
        user.encodePassword()
        if (oldUser)
            user['remainingOldAds'] = OldAdvertisement.countByUserIdAndApproved(oldUser?.id as Integer, true)
        if (!user.save(flush: true)) {
            return [error: user.errors.allErrors.collect { message(it.code) }, field: mobile ? 'mobile' : 'email']
        }
        if (!oldUser) {
            if (mobile) {
                SmsHelper.sendSms(mobile, message('register.sms', [password.toString()]))
            }
            if (email) {
                mailService.sendMail {
                    to email
                    subject message('register.email')
                    html(view: "/messageTemplates/email_template",
                            model: [message: g.render(template: '/messageTemplates/mail/register', model: [user: user, password: password]).toString(),
                                    source : 'registration',
                                    email  : email])
                }
            }
        }
        return [success: true]
    }

    def resetPassword(String username) {
        def user = User.findByEmailOrMobileOrUsername(username, username, username)
        if (!user) {
            def oldUser = OldUser.findByEmailOrMobileNumber(username, username)
            if (!oldUser || !register(oldUser.mobileNumber ?: '', oldUser.email, oldUser.fullName, '', true, true)?.success)
                return message('user.resetPassword.error.noSuchUser')
            else
                user = User.findByEmailOrMobileOrUsername(username, username, username)
        }

        def password = (Math.random() * 900000 + 100000) as int
        user.password = password
        user.encodePassword()
        user['changedPassword'] = false
        user.save()

        if (user.mobile) {
            SmsHelper.sendSms(user.mobile, message('resetPassword.sms', [password.toString()]))
        }
        if (user.email) {
            mailService.sendMail {
                to user.email
                subject message('register.email')
                html(view: "/messageTemplates/email_template",
                        model: [message: g.render(template: '/messageTemplates/mail/resetPassword', model: [user: user, password: password]).toString(),
                                source : 'resetPassword',
                                email  : user?.email])
            }
        }
        return null
    }

    def changePassword(String oldPassword, String newPassword) {
        def user = springSecurityService.currentUser as User
        if (!user['changedPassword'] || user.password == springSecurityService.encodePassword(oldPassword)) {
            user.password = newPassword
            user.encodePassword()
            user['changedPassword'] = true
            user.save()
            springSecurityService.currentUser['changedPassword'] = true
            return null
        } else {
            [
                    error : message('user.changePassword.invalidOldPassword'),
                    action: user['changedPassword'] ? 'changePassword' : 'mustChangePassword'
            ]
        }
    }

    def updateBasicInfo(String profileImage, String mobile, String email, String firstName, String lastName, Boolean male, String province, String city, String bio) {
        def user = springSecurityService.currentUser as User
        def countRegistered = mongoService.getCollection('user').count([$and: [[$or: [[mobile: mobile ?: '-'], [email: email ?: '-']]], ['_id': [$ne: user.id]]]])
        if (countRegistered > 0) {
            return [error: message('user.exists'), field: mobile ? 'mobile' : 'email']
        }
        if (profileImage) {
            Image.findAllByTypeAndOwnerId('profile', user?.id?.toString()).each {
                it.delete(flush: true)
            }
            imageService.saveImage(profileImage, 'profile', user?.id?.toString())
        }
        user['mobile'] = mobile
        user['email'] = email
        user['firstName'] = firstName
        user['lastName'] = lastName
        user['gender'] = male ? 'male' : 'female'
        user['province'] = province
        user['city'] = city
        user['bio'] = bio
        if (!user.save()) {
            return [error: user.errors.allErrors.collect { message(it.code) }, field: mobile ? 'mobile' : 'email']
        }
        return [success: true]
    }

    def findUser(String username) {
        return mongoService.getCollection('user').find([$or: [[mobile: username], [email: username], [username: username]]]).find()
    }
}
