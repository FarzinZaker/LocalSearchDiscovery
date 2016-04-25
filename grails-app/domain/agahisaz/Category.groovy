package agahisaz

class Category {

    static mapWith = "mongo"

    String name
    String pluralName
    String englishName
    String englishPluralName
    String iconPath
    Category parent

    public transient String getIconDirectory(){
        iconPath?.split('/')?.first()
    }

    public transient String getIconFile(){
        iconPath?.split('/')?.last()
    }

    static constraints = {
        name blank: false
        pluralName blank: false
        englishName blank: false
        englishPluralName nullable: true
        iconPath nullable: true
        parent nullable: true
    }
}
