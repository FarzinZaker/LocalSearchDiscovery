package agahisaz

import grails.util.Environment


class SearchIndexJob {

    def startDelay = 60000
    def timeout = 5 * 60 * 1000l
    def concurrent = false

    def elasticSearchService
    def mongoService

    def execute() {
        if (Environment.isDevelopmentMode())
            return
        def places
        places = Place.findAllByIndexedIsNullOrIndexed(false, [max: 500])
        println("[SEARCH INDEX]: ${Place.countByIndexedIsNullOrIndexed(false)} items remaing")

        if (places?.size()) {
            try {
                elasticSearchService.index(places)
            }
            catch (ignored) {
                ignored?.printStackTrace()
            }
            finally {
                mongoService.getCollection('place').update(
                        [_id: [$in: places*.id]],
                        [$set: [
                                'indexed': true
                        ]],
                        false, true
                )
            }
        }
    }
}
