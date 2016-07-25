package agahisaz

import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.codehaus.groovy.grails.web.util.WebUtils

class AgahisazUserDetailsService extends GormUserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
        def request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        def session = request.session
        def loginErrorsCount = session["loginErrorsCount"] ?: 0
        if (loginErrorsCount > 0) {
            def captcha = request.parameterMap['captcha']?.find()
            if (captcha != session['loginCaptcha'].toString()) {
                throw new LockedException('captcha')
            }
        }

        def conf = SpringSecurityUtils.securityConfig
        String userClassName = conf.userLookup.userDomainClassName
        def dc = grailsApplication.getDomainClass(userClassName)
        if (!dc) {
            throw new RuntimeException("The specified user domain class '$userClassName' is not a domain class")
        }

        Class<?> User = dc.clazz

        User.withTransaction { status ->
            def user = User.findByUsernameOrEmailOrMobile(username, username, username, [max: 1])
            if (!user) {
                throw new UsernameNotFoundException('User not found', username)
            }

            Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
            createUserDetails user, authorities
        }
    }
}
