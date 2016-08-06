package agahisaz

import com.pars.agahisaz.User

class DelayedMessage {

    static mapWith = "mongo"

    String title
    String body
    String type
    User receiver
    Date sendDate

    static constraints = {
    }
}
