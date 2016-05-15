package agahisaz

import grails.converters.JSON

class TagController {

    def mongoService

    def search() {
        def tags = mongoService.getCollection('tag').find([name:[$regex:".*${params.id}.*"]]).findAll()
        render(tags.collect { [name: it.name] } as JSON)
    }
}
