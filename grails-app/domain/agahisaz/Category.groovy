package agahisaz

class Category {

    static searchable = {
        root = false
        except = ['parent', 'parentIdList', 'childIdList']
        name boost: 6.0
    }

    static mapWith = "mongo"

    String name
    String pluralName
    String englishName
    String englishPluralName
    String iconPath
    Category parent

    List<Long> parentIdList = []
    List<Long> childIdList = []

    static transients = ['parentIdList', 'childIdList']

    static mapping = {
//        index name: 'text'
    }

    static constraints = {
        name blank: false
        pluralName blank: false
        englishName blank: false
        englishPluralName nullable: true
        iconPath nullable: true
        parent nullable: true
    }

    public transient String getSearchString() {
        "${name},${pluralName},${englishName},${englishPluralName}" +
                (parent ? ',' + parent?.searchString : '')
    }
}
