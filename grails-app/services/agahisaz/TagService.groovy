package agahisaz

import utils.StringHelper

class TagService {

    def importFromFile() {
        def lines = CategoryService.classLoader.getResource('data/tags.csv').readLines('UTF-8')
        lines.remove(0)
        lines.each {
            def line = StringHelper.normalize(it).split(',')
            def name = line[1].trim()
            def tag = Tag.findByName(name)
            if (!tag) {
                tag = new Tag()
                tag.name = name;
                tag.save(flush: true)
            }
        }
    }
}
