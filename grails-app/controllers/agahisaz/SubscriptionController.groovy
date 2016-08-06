package agahisaz

import security.AuthenticationProvider

class SubscriptionController {

    def add() {

        def email = params.email?.toString()?.toLowerCase()
        def source = params.source?.toString()?.toLowerCase()
        def token = params.token

        if (AuthenticationProvider.md5("subscribe ${email} in ${source} of agahisaz") == token) {
            def unsubscribeList = Unsubscribe.findAllByEmailAndRemoved(email, false)
            unsubscribeList.each { unsubscribe ->
                unsubscribe.removed = true
                unsubscribe.removeSource = source
                unsubscribe.removeDate = new Date()
                unsubscribe.save(flush: true)
            }
            [email: email, source: source, removeToken: AuthenticationProvider.md5("unsubscribe ${email} from ${source} of agahisaz")]
        } else [email: null]
    }

    def remove() {

        def email = params.email?.toString()?.toLowerCase()
        def source = params.source?.toString()?.toLowerCase()
        def token = params.token

        if (AuthenticationProvider.md5("unsubscribe ${email} from ${source} of agahisaz") == token) {
            if(!Unsubscribe.findByEmailAndAddSourceAndRemoved(email, source, false)) {
                def unsubscribe = new Unsubscribe()
                unsubscribe.email = email
                unsubscribe.addSource = source
                unsubscribe.addDate = new Date()
                unsubscribe.save(flush: true)
            }
            [email: email, source: source, addToken: AuthenticationProvider.md5("subscribe ${email} in ${source} of agahisaz")]
        } else [email: null]
    }
}
