package agahisaz

class Category {

    static mapWith = "mongo"

    String name
    String pluralName
    String englishName
    String englishPluralName
    String iconPath
    Category parent

    public String getIconDirectory() {
        iconPath?.split('/')?.first() ?: ''
    }

    public String getIconFile(String size) {
        def path = iconPath?.split('/')?.last()
        if (path)
            path + size + ".png"
        else
            'no-image.png'
    }

    List<Long> parentIdList = []
    List<Long> childIdList = []

    static transients = ['iconDirectory', 'parentIdList', 'childIdList']

    static constraints = {
        name blank: false
        pluralName blank: false
        englishName blank: false
        englishPluralName nullable: true
        iconPath nullable: true
        parent nullable: true
    }
}
