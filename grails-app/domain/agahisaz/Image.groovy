package agahisaz

import org.bson.types.Binary

class Image {

    String ownerId
    String type
    Integer size
    Binary bytes
    Byte[] content

    static constraints = {
        content(nullable: true)
    }
}
