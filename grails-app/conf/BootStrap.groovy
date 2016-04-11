import com.pars.agahisaz.Role
import com.pars.agahisaz.User
import com.pars.agahisaz.UserRole

class BootStrap {

    def init = { servletContext ->
        def r = Role.findByAuthority('ADMIN') ?: new Role(authority: 'ADMIN').save(flush: true)
        def u = User.findByUsername('admin')
        if (!u) {
            u = new User(username: 'admin', password: 'admin', enabled: true).save()
            UserRole.create(u, r, true)
        }
    }
    def destroy = {
    }
}
