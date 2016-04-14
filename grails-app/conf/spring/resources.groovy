import agahisaz.AgahisazUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import utils.AgahisazAuthenticationProvider

// Place your Spring DSL code here
beans = {
    userDetailsService(AgahisazUserDetailsService) {
        grailsApplication = ref('grailsApplication')
    }
}
