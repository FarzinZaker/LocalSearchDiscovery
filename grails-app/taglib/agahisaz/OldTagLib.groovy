package agahisaz

import agahisaz.old.OldAdvertisement
import agahisaz.old.OldUser
import com.pars.agahisaz.User

class OldTagLib {

    def springSecurityService
    static namespace = "old"

    def adsMenu = { attrs, body ->
        def currentUser = springSecurityService.currentUser as User
        def count = currentUser['remainingOldAds'] as Integer
        if (count > 0) {
            out << render(template: '/layouts/common/oldAdsMenu', model: [count: count])
        }
    }

    def adBody = {attrs, body ->
        def ad = attrs.ad as OldAdvertisement
        if(ad){
            def result = ad.body?.replaceAll("<(.|\n)*?>", '')
            if(result.length() > 500)
                result = result.substring(0, 500) + ' ...'
            out << result
        }
    }
}
