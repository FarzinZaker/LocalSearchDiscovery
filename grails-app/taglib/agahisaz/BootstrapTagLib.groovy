package agahisaz

class BootstrapTagLib {

    static namespace = "bootstrap"

    def modal = { attrs, body ->
        pageScope.modalFooter = ''
        out << render(template: '/common/modal', model: [id: attrs.id, body: body(), footer: pageScope.modalFooter])
    }

    def modalFooter = { attrs, body ->
        pageScope.modalFooter = body()

    }

    def loading = { attrs, body ->
        out << render(template: '/common/loading', model: [size: attrs.size ?: '48', id: attrs.id])
    }
}
