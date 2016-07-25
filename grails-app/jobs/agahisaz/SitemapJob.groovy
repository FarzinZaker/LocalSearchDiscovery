package agahisaz


class SitemapJob {

    static startDelay = 60000
    static timeout = 5000l
    static concurrent = false

    def siteMapService

    def execute() {
        siteMapService.refresh()
    }
}
