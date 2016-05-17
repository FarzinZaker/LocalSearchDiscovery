package agahisaz

import utils.StringHelper

class CategoryService {

    def getAllData() {
        def categories = Category.list().collect {
            it.map = [
                    id      : it.id,
                    name    : it.name,
                    parent  : it.parent,
                    children: []
            ]
            it.map
        }
        def res = []
        categories.each {
            if (it.parent)
                it.parent.map.children << [id: it.id, name: it.name, children: it.children]
            else
                res << [id: it.id, name: it.name, children: it.children]
        }
        res
    }

    def importFromFile() {
        def lines = CategoryService.classLoader.getResource('data/cats.csv').readLines('UTF-8')
//        lines.remove(0)
        lines.each {
            def line = StringHelper.normalize(it).split(',')
            def englishName = line[1].trim()
            def category = Category.findByEnglishName(englishName)
            if (!category)
                category = new Category(englishName: englishName)
            category.name = line[3].trim()
            category.pluralName = line[4].trim()
            category.englishPluralName = line[2].trim()
            category.parent = Category.findByEnglishPluralName(line[0].trim()) ?: Category.findByEnglishName(line[0].trim())
            if(!category.parent){
                println ''
            }
            category.save(flush: true)
        }
    }

    def importIconsFromFile() {
        def lines = CategoryService.classLoader.getResource('data/catIcons.csv').readLines('UTF-8')
        lines.each {
            def line = it.split(',')
            def englishName = line[0].trim()
            def category = Category.findByEnglishName(englishName) ?: Category.findByEnglishPluralName(englishName)
            if (category) {
                category.iconPath = line[1].trim()
                category.save(flush: true)
            }
        }
    }
}
