package agahisaz

import grails.util.Environment


class SitemapJob {

    static startDelay = 60000
//    static timeout = 12 * 60 * 60 * 1000l
    static cronExpression = '0 0 1 1/1 * ? *'
    static concurrent = false

    def siteMapService

    def execute() {

        if(Environment.isDevelopmentMode())
            return

        siteMapService.refresh()
    }
}
