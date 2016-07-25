package security

import agahisaz.Action
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/**
 * Created by root on 9/7/14.
 */
public class LoginEvent extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        request.session.removeAttribute('loginErrorsCount')
        super.onAuthenticationSuccess(request, response, authentication)

        def actionService = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT).actionService
        actionService.doAction(Action.LOGIN)
    }
}