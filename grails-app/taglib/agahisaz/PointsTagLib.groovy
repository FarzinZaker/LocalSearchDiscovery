package agahisaz

import com.pars.agahisaz.User

class PointsTagLib {

    def mongoService

    static namespace = "points"

    def topUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$sort: [totalScore: -1]],
                [$limit: 6]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it, score:it.totalScore])
        }
    }

    def weekTopUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$sort: [weekScore: -1]],
                [$limit: 6]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it, score:it.weekScore])
        }

    }
}