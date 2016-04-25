package agahisaz

import grails.converters.JSON

class DataController {

    def cityService
    def categoryService

    def cities() {
        render(cityService.allData as JSON)
    }

    def categories() {
        render(categoryService.allData as JSON)
    }
}
