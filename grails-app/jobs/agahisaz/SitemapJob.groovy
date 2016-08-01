package agahisaz


class SitemapJob {

    static startDelay = 60000
    static timeout = 12 * 60 * 60 * 1000l
    static concurrent = false

    def siteMapService

    def execute() {
        siteMapService.refresh()
    }
}
