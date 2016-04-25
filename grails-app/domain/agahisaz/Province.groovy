package agahisaz

class Province {

    static mapWith = "mongo"

    String name
    List cities

    static embedded = ['cities']

    static constraints = {
        name blank: false
    }
}
