package agahisaz

import com.pars.agahisaz.User
import grails.converters.JSON
import search.GeoPoint

class Place {

    static searchable = {
        only = ['name', 'province', 'city', 'address', 'phone', 'postalCode', 'tags', 'location','locationString', 'tipsBody', 'category', 'approved', 'reportType', 'averageRate', 'ratesCount']
//        except = ['creator', 'editSuggestion']
        locationString geoPoint: true//, component: true
        name boost: 10.0
        category component: true
        tags analyzer:'keyword', boost: 3.0
        tipsBody boost: 1.0
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

    String distance

    static hasMany = [tags: String, tipsBody: String, location: Double]

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

    public transient String getTipsBody() {
        tips?.collect { it?.body }
    }

    public transient String getLocationString() {
        location?.collect { it?.toString() }?.join(',')
    }

    static mapping = {
        location geoIndex: true
//        index name: 'text', address: 'text', indexAttributes: [name: 10, address: 1, tags: 5]
        compoundIndex province: 1, city: 1, category: 1
        tags index: true
        indexed index: true
        locallyIndexed index: true
    }

    def afterUpdate() {
        if (!dateCreated)
            dateCreated = new Date()

        indexed = false
        locallyIndexed = false
//        save(flush: true)
    }
}
