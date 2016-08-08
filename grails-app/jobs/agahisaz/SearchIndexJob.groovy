package agahisaz

import grails.util.Environment


class SearchIndexJob {

    def startDelay = 60000
    def timeout = 5000l
    def concurrent = false

    def elasticSearchService
    def mongoService

    def execute() {
        def place
        if (Environment.isDevelopmentMode()) {
            place = Place.findByLocallyIndexedIsNullOrLocallyIndexed(false)
            println("[SEARCH INDEX]: ${Place.countByLocallyIndexedIsNullOrLocallyIndexed(false)} items remaing")
        } else {
            place = Place.findByIndexedIsNullOrIndexed(false)
            println("[SEARCH INDEX]: ${Place.countByIndexedIsNullOrIndexed(false)} items remaing")
        }

        if (place) {
            try {
                elasticSearchService.index(place)
            }
            catch (ignored) {
                println("[SEARCH INDEX]: failed to index place : ${place?.id}")
                ignored?.printStackTrace()
            }
            finally {
                if (Environment.isDevelopmentMode())
                    mongoService.getCollection('place').update(
                            [_id: place?.id],
                            [$set: [
                                    'locallyIndexed': true
                            ]]
                    )
                else
                    mongoService.getCollection('place').update(
                            [_id: place?.id],
                            [$set: [
                                    'indexed': true
                            ]]
                    )
            }
        }
    }
}
