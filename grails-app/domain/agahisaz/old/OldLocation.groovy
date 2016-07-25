package agahisaz.old

class OldLocation {

    String name

    static mapping = {
//        datasource 'sqlServer'

        table 'Location'
        version false

        name column: 'DisplayName'
    }

    static constraints = {
    }
}
