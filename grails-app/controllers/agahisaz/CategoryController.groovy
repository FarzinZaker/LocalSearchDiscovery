package agahisaz

import grails.converters.JSON

class CategoryController {

    def mongoService

    def search() {
        def categories = mongoService.query('category', [name: [$regex: ".*${params.id}.*"]]).findAll()
        render(categories.collect {
            def icon = resource(dir: 'images/categories/' + it?.iconPath?.split('/')?.first(), file: it?.iconPath?.split('/')?.last() + '32.png')
            if (!it?.iconPath?.contains('/'))
                icon = resource(dir: 'images/categories/', file: 'no-image.png')
            [
                    name: it?.name,
                    icon: icon
            ]
        } as JSON)
    }
}
