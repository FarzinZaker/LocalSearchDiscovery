package security

import com.pars.agahisaz.User
import grails.util.Environment
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.RememberMeAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.web.context.request.RequestContextHolder

import javax.security.auth.login.CredentialExpiredException
import javax.servlet.http.HttpSession
import java.security.MessageDigest

class AuthenticationProvider extends DaoAuthenticationProvider {
    def springSecurityService
    def grailsApplication

    Authentication authenticate(Authentication authentication) {

        if (authentication instanceof RememberMeAuthenticationToken) {
            return authentication
        } else if (authentication.principal == 'oAuthLogin') {

            def user = session['oAuthLogin'] as User
            session['oAuthLogin'] = null
            if (user) {

                checkUserFlags(user)

                GrantedAuthorityImpl[] authorities = user.authorities.collect {
                    new GrantedAuthorityImpl(it.authority)
                }
                def userDetails = new GrailsUser(user.username, user.password, user.enabled, !user.accountExpired, true, !user.accountLocked, authorities as Collection<GrantedAuthority>, user.id)
                def token = new UsernamePasswordAuthenticationToken(userDetails, user.password, userDetails.authorities)
                token.details = authentication.details
                return token
            } else return super.authenticate(authentication);
        } else {
            User.withTransaction { status ->
                def user = User.findByUsername(authentication.principal as String)
                if (user) {
                    if (user?.password == springSecurityService.encodePassword(authentication.credentials)) {

                        checkUserFlags(user)

                        GrantedAuthorityImpl[] authorities = user.authorities.collect {
                            new GrantedAuthorityImpl(it.authority)
                        }
                        def userDetails = new GrailsUser(user.username, user.password, user.enabled, !user.accountExpired, true, !user.accountLocked, authorities as Collection<GrantedAuthority>, user.id)
                        def token = new UsernamePasswordAuthenticationToken(userDetails, user.password, userDetails.authorities)
                        token.details = authentication.details
                        return token
                    } else {
                        throw new BadCredentialsException("Log in failed - identity could not be verified");
                    }
                } else return super.authenticate(authentication);
            }
        }
    }

    private static checkUserFlags(User user) {
        if (user.accountLocked)
            throw new LockedException(user.username)
        if (user.accountExpired)
            throw new AccountExpiredException(user.username)
        if (user.passwordExpired)
            throw new CredentialsExpiredException(user.username)
        if (!user.enabled)
            throw new DisabledException(user.username)
    }

    private static HttpSession getSession() {
        return RequestContextHolder.currentRequestAttributes().getSession()
    }

    public static String md5(String text) {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());

        byte[] byteData = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        hexString
    }

    public static String sha1(String text) {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(new BigInteger(text, 16).toByteArray());

        byte[] byteData = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        hexString
    }

    boolean supports(Class authentication) {
        return true
    }
}