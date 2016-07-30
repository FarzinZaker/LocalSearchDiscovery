package agahisaz

class SitemapController {

    def grailsApplication

    def index() {
        try {
            response.contentType = 'text/xml'
            new File("${grailsApplication.config.sitemap.path}index.xml").withInputStream {
                response.outputStream << it
            }
            response.outputStream.flush()
        }
        catch (ignored) {
            response.status = 404;
        }
    }

    def view() {
        try {
            response.contentType = 'text/xml'
            new File("${grailsApplication.config.sitemap.path}${params.type}-${params.id}.xml").withInputStream {
                response.outputStream << it
            }
            response.outputStream.flush()
        }
        catch (ignored) {
            response.status = 404;
        }
    }
}
