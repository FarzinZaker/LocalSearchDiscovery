package agahisaz

import com.pars.agahisaz.User

class PointsTagLib {

    def mongoService

    static namespace = "points"

    def topUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$sort: [score: -1]],
                [$limit: 6]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it])
        }
    }

    def weekTopUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$sort: [score: -1]],
                [$limit: 6]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it])
        }
    }
}