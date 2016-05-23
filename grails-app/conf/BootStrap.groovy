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
    def mongoService

    def init = { servletContext ->

        authenticationFailureHandler.redirectStrategy = new CustomRedirectStrategy()

//        mongoService.createIndex('place', 'place_search_index', [name: "text", address: "text", tags: "text"], [name: 10, address: 1, tags: 5])

        def r = Role.findByAuthority(Roles.USER) ?: new Role(authority: Roles.USER).save(flush: true)
        r = Role.findByAuthority(Roles.ADMIN) ?: new Role(authority: Roles.ADMIN).save(flush: true)
        def u = User.findByUsername('admin')
        if (!u) {
            u = new User(username: 'admin', password: 'admin', enabled: true, superuserLevel: 10).save()
            UserRole.create(u, r, true)
        } else if (u.superuserLevel == null) {

            u.superuserLevel = 10
            u.save(flush: true)
        }

//        categoryService.importFromFile()
//        categoryService.importIconsFromFile()
//        cityService.importFromFile()
//        tagService.importFromFile()
    }
    def destroy = {
    }
}
