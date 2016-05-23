package agahisaz

class Image {

    String ownerId
    String type
    Integer size
    Byte[] content

    static constraints = {
    }

    static mapping = {
        compoundIndex type: 1, ownerId: 1, size: 1
    }
}
