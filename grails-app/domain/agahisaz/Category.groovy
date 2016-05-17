package agahisaz

class Category {

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

    static constraints = {
        name blank: false
        pluralName blank: false
        englishName blank: false
        englishPluralName nullable: true
        iconPath nullable: true
        parent nullable: true
    }
}
