package agahisaz

import com.pars.agahisaz.User
import search.GeoPoint

class Place {

    static searchable = {
        only = ['name', 'province', 'city', 'address', 'phone', 'postalCode', 'categoryString', 'tagsString', 'tipsString']
        geoPoint geoPoint: true, component: true
        name boost: 10.0
        categoryString boost: 8.0
        tagsString boost: 4.0
        tipsString boost: 2.0
    }

    static mapWith = "mongo"

    String name
    String city
    String province
    String address
    String phone
    String postalCode
    Category category
    List location
    List tags
    User creator
    EditSuggestion editSuggestion
    List tips
    List reportedTips
    List rates
    Double averageRate
    Integer ratesCount

    Boolean approved = false

    //report fields
    String reportType
    String reportComment

    Date dateCreated
    Date lastUpdated

    Boolean indexed = false
    Boolean locallyIndexed = false

    static constraints = {
        name blank: false
        address nullable: true
        phone nullable: true
        postalCode nullable: true
        editSuggestion nullable: true
        averageRate nullable: true
        ratesCount nullable: true

        approved nullable: true

        reportType nullable: true
        reportComment nullable: true

        dateCreated nullable: true
        lastUpdated nullable: true

        indexed nullable: true
        locallyIndexed nullable: true
    }

    public transient GeoPoint getGeoPoint() {
        new GeoPoint(location?.first() as Double, location?.last() as Double)
    }

    public transient String getTagsString() {
        tags?.join(',') ?: ''
    }

    public transient String getTipsString() {
        tips?.collect { it?.body }?.join(',') ?: ''
    }

    public transient String getCategoryString() {
        category?.searchString ?: ''
    }

    static mapping = {
        location geoIndex: true
//        index name: 'text', address: 'text', indexAttributes: [name: 10, address: 1, tags: 5]
        compoundIndex province: 1, city: 1, category: 1
        tags index: true
    }

    def beforeUpdate() {
        if (!dateCreated)
            dateCreated = new Date()

        indexed = false
        locallyIndexed = false
    }
}
