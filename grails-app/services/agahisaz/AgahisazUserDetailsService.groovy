package agahisaz

import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class AgahisazUserDetailsService extends GormUserDetailsService {

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException {
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
                log.warn "User not found: $username"
                throw new UsernameNotFoundException('User not found', username)
            }

            Collection<GrantedAuthority> authorities = loadAuthorities(user, username, loadRoles)
            createUserDetails user, authorities
        }
    }
}
