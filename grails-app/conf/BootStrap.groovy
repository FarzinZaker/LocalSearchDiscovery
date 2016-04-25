
import com.pars.agahisaz.Role
import com.pars.agahisaz.User
import com.pars.agahisaz.UserRole
import security.CustomRedirectStrategy
import security.Roles

class BootStrap {

    def authenticationFailureHandler
    def categoryService
    def cityService
    def tagService

    def init = { servletContext ->

        authenticationFailureHandler.redirectStrategy = new CustomRedirectStrategy()


        def r = Role.findByAuthority(Roles.USER) ?: new Role(authority: Roles.USER).save(flush: true)
        r = Role.findByAuthority(Roles.ADMIN) ?: new Role(authority: Roles.ADMIN).save(flush: true)
        def u = User.findByUsername('admin')
        if (!u) {
            u = new User(username: 'admin', password: 'admin', enabled: true).save()
            UserRole.create(u, r, true)
        }

        categoryService.importFromFile()
        categoryService.importIconsFromFile()
        cityService.importFromFile()
        tagService.importFromFile()
    }
    def destroy = {
    }
}
