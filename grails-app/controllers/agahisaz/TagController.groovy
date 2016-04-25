package agahisaz

import grails.converters.JSON

class TagController {

    def mongoService

    def search() {
        def tags = mongoService.query('tag',[name:[$regex:".*${params.id}.*"]]).findAll()
        render(tags.collect { [name: it.name] } as JSON)
    }
}
