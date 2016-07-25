package agahisaz.old

class OldTag {

    String name
    Integer itemsCount

    static mapping = {
        table 'Tag'
        version false

        name column: 'DisplayName'
        itemsCount column: 'ItemsCount'
    }

    static constraints = {
    }
}
