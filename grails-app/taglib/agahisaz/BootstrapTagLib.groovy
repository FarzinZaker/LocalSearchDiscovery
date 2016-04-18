package agahisaz

import org.apache.commons.lang.IncompleteArgumentException

class BootstrapTagLib {

    static namespace = "bootstrap"

    def modal = { attrs, body ->
        pageScope.modalFooter = ''
        out << render(template: '/common/modal', model: [id: attrs.id, size: attrs.size, body: body(), footer: pageScope.modalFooter])
    }

    def modalFooter = { attrs, body ->
        pageScope.modalFooter = body()

    }

    def loading = { attrs, body ->
        out << render(template: '/common/loading', model: [size: attrs.size ?: '48', id: attrs.id])
    }

    def captcha = { attrs, body ->
        if (!attrs.name)
            throw new IncompleteArgumentException('name')
        if (!attrs.type)
            throw new IncompleteArgumentException('type')
        out << render(template: '/common/captcha', model: [name: attrs.name, id: attrs.id ?: attrs.name, type: attrs.type])
    }
    def cropie = { attrs, body ->
        if (!attrs.name)
            throw new IncompleteArgumentException('name')
        out << render(template: '/common/cropie', model: [name: attrs.name, cssClass: attrs.cssClass, src: attrs.src])
    }

    def verticalTabStrip = { attrs, body ->

        pageScope.tabPageHeaders = []
        pageScope.tabPageContents = []
        body()

        out << """

        <div class="row">
            <div class="col-xs-9">
                <div class="tab-content tab-content-left whitePanel ${attrs.cssClass}">
"""
        pageScope.tabPageContents.each {
            out << it
        }
        out << """
                </div>
            </div>
            <div class="col-xs-3">
                <ul class="nav nav-tabs tabs-left">
"""
        pageScope.tabPageHeaders.each {
            out << it
        }
        out << """
                </ul>
            </div>

            <div class="clearfix"></div>
            </div>
"""
    }

    def tabPage = { attrs, body ->
        def currentTab = pageScope.currentTab
        pageScope.tabPageHeaders << """
                    <li class="${currentTab == attrs.id ? 'active' : ''}"><a href="#${attrs.id}" data-toggle="tab">${
            attrs.title
        }</a></li>
"""
        pageScope.tabPageContents << """
                    <div class="tab-pane ${currentTab == attrs.id ? 'active' : ''}" id="${attrs.id}">
                        ${body()}
                    </div>
"""
    }
}
