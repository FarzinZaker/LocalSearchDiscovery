package agahisaz.old


class OldAdvertisement {

    String title
    String body
    String authorName
    String phoneNumber
    String faxNumber
    String price
    String address
    String linkUrl
    String pictureUrl1
    String pictureUrl2
    String pictureUrl3
    String pictureUrl4
    Integer points
    Integer visitCount
    Integer clickCount
    Integer locationId
    Integer userId
    Integer categoryId
    Boolean approved
    String email
    Float currentRate
    Integer ratesCount

    static mapping = {
//        datasource 'sqlServer'

        table 'Advertisement'
        version false

        title column: 'Title'
        body column: 'Body'
        authorName column: 'AuthorName'
        phoneNumber column: 'PhoneNumber'
        faxNumber column: 'FaxNumber'
        price column: 'Price'
        address column: 'Address'
        linkUrl column: 'LinkUrl'
        pictureUrl1 column: 'PictureUrl'
        pictureUrl2 column: 'PictureUrl2'
        pictureUrl3 column: 'PictureUrl3'
        pictureUrl4 column: 'PictureUrl4'
        points column: 'Points'
        visitCount column: 'VisitCount'
        clickCount column: 'ClickCount'
        locationId column: 'LocationId'
        userId column: 'UserId'
        categoryId column: 'CategoryId'
        approved column: 'Approved'
        email column: 'Email'
        currentRate column: 'CurrentRate'
        ratesCount column: 'RatesCount'
    }

    static constraints = {
    }
}
