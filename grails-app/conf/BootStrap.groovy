import agahisaz.Image
import com.pars.agahisaz.Role
import com.pars.agahisaz.User
import com.pars.agahisaz.UserRole
import org.bson.types.Binary
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
//        Thread.startDaemon {
//            while (true) {
//                try {
//                    Thread.sleep(100)
//                    def c = System.currentTimeMillis()
////                Thread.startDaemon {
//                    def trs = []
//                    Image.findAllByContentIsNotNull([max: 10]).eachWithIndex { itbc, i ->
//
//                        trs << Thread.start {
//                            def itb = Image.get(itbc.id)
//                            itb.bytes = new Binary(itb.content as byte[])
//                            itb.content = null
//                            itb.save(flush: true)
//                        }
//
//                    }
//                    while (trs.size() > 0) {
//                        Thread.sleep(10)
//                        trs = trs.findAll { it.isAlive() }
//                    }
//                    println('finished============================================' + (System.currentTimeMillis() - c).toString())
//                }catch (x){
//                }
////                }.join()
//
//            }
//
//        }
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

        categoryService.importFromFile()
        categoryService.importIconsFromFile()
        cityService.importFromFile()
        tagService.importFromFile()
    }
    def destroy = {
    }
}
