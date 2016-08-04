package agahisaz

import com.mongodb.DBCursor
import grails.converters.JSON

class CategoryController {

    def mongoService

    def search() {
        def cursor = mongoService.getCollection('category').find([name: [$regex: ".*${params.id}.*"]])
        try {
            def categories = cursor.findAll()
            render(categories.collect {
                [
                        name: it?.name,
                        icon: createLink(controller: 'image', action: 'category', params: [id: it?._id, size: 32])
                ]
            } as JSON)
        }
        catch (ignored) {
            render([] as JSON)
        }
        finally {
            cursor?.close()
        }
    }
}
