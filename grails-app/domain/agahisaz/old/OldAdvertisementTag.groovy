package agahisaz.old

class OldAdvertisementTag {

    Long tagId
    Long advertisementId

    static mapping = {
        table 'AdvertisementTag'
        version false

        tagId column: 'TagId'
        advertisementId column: 'AdvertisementId'
    }

    static constraints = {
    }
}
