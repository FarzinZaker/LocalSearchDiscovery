package agahisaz

import com.pars.agahisaz.User

class PointsTagLib {

    def mongoService

    static namespace = "points"

    def topUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$match: [_id: [$gt: 2]]],
                [$sort: [totalScore: -1, _id: -1]],
                [$limit: 10]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it, score: it.totalScore])
        }
    }

    def weekTopUsers = { attrs, body ->
        def users = mongoService.getCollection('user').aggregate(
                [$match: [_id: [$gt: 2]]],
                [$sort: [weekScore: -1, _id: -1]],
                [$limit: 10]
        ).results()
        users.each {
            out << render(template: '/points/userCard', model: [user: it, score: it.weekScore])
        }

    }
}