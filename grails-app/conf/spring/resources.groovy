import agahisaz.AgahisazUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import security.LoginEvent
import security.AuthenticationProvider

// Place your Spring DSL code here
beans = {

    sessionBinderListener(NopSessionBinderJobListener) { }

    userDetailsService(AgahisazUserDetailsService) {
        grailsApplication = ref('grailsApplication')
    }

    securityContextLogoutHandler(SecurityContextLogoutHandler) {
        invalidateHttpSession = false
    }

    authenticationSuccessHandler(LoginEvent) {

        def conf = SpringSecurityUtils.securityConfig
        requestCache = ref('requestCache')
        defaultTargetUrl = conf.successHandler.defaultTargetUrl
        alwaysUseDefaultTargetUrl = conf.successHandler.alwaysUseDefault
        targetUrlParameter = conf.successHandler.targetUrlParameter
        useReferer = conf.successHandler.useReferer
        redirectStrategy = ref('redirectStrategy')
    }
}
