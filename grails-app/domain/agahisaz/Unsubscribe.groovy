package agahisaz

class Unsubscribe {

    static mapWith = "mongo"

    String email
    Date addDate
    String addSource
    Boolean removed = false
    Date removeDate
    String removeSource


    static mapping = {
    }

    static constraints = {
        removeDate nullable: true
        removeSource nullable: true
    }
}
