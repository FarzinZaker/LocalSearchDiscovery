package agahisaz

import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest

class OverrideTagLib extends ApplicationTagLib {

    Closure createLink = { attrs ->
        def specialChars = ['-', ')', '(', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '_', '+', '=', '{', '}', '\\', '[', ']', '|', ';', ':', '\'', '"', '?', '/', ',', '.', '<', '>']

        if (attrs.id && attrs.id instanceof String) {
            specialChars.each { attrs.id = attrs.id.replace(it, ' ') }
            while (attrs.id.contains('  '))
                attrs.id = attrs.id.replace('  ', ' ')
        }
        if (attrs.params?.id && attrs.params?.id instanceof String) {
            specialChars.each { attrs.params?.id = attrs.params.id.replace(it, ' ') }
            while (attrs.params?.id?.contains('  '))
                attrs.params.id = attrs.params?.id?.replace('  ', ' ')
        }

        if (attrs.params?.name && attrs.params?.name instanceof String) {
            specialChars.each { attrs.params?.name = attrs.params.name.replace(it, ' ') }
            while (attrs.params?.name?.contains('  '))
                attrs.params.name = attrs.params?.name?.replace('  ', ' ')
        }


        def urlAttrs = attrs
        if (attrs.url instanceof Map) {
            urlAttrs = attrs.url
        }
        def params = urlAttrs.params && urlAttrs.params instanceof Map ? urlAttrs.params : [:]
        if (request['flowExecutionKey']) {
            params."execution" = request['flowExecutionKey']
            urlAttrs.params = params
            if (attrs.controller == null && attrs.action == null && attrs.url == null && attrs.uri == null) {
                urlAttrs[LinkGenerator.ATTRIBUTE_ACTION] = GrailsWebRequest.lookup().actionName
            }
        }
        if (urlAttrs.event) {
            params."_eventId" = urlAttrs.remove('event')
            urlAttrs.params = params
        }
        def generatedLink = linkGenerator.link(attrs, request.characterEncoding)

        if (useJsessionId) {
            return response.encodeURL(generatedLink)
        } else {
            return generatedLink
        }
    }
}