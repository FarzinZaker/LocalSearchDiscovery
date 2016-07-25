package agahisaz

class TestController {

    def siteMapService

    def index() {
        siteMapService.refresh()
    }
}
