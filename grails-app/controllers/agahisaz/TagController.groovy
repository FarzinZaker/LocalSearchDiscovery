package agahisaz

import grails.converters.JSON

class TagController {

    def mongoService

    def search() {
        def cursor = mongoService.getCollection('tag')?.find([name:[$regex:".*${params.id}.*"]])
        def tags = []
        try {
            tags = cursor?.findAll()
        }
        finally {
            cursor?.close()
        }
        render(tags.collect { [name: it.name] } as JSON)
    }
}
