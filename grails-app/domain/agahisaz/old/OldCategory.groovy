package agahisaz.old

class OldCategory {

    String name
    Long parentCategoryId

    static mapping = {
        table 'Category'
        version false
        name column: 'DisplayName'
        parentCategoryId column: 'parentCategoryId'
    }

    static constraints = {
    }
}
