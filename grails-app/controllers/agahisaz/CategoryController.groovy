package agahisaz

import grails.converters.JSON

class CategoryController {

    def mongoService

    def search() {
        def categories = mongoService.getCollection('category').find([name: [$regex: ".*${params.id}.*"]]).findAll()
        render(categories.collect {
            [
                    name: it?.name,
                    icon: createLink(controller: 'image', action: 'category', params: [id: it?._id, size: 32])
            ]
        } as JSON)
    }
}
