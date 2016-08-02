package agahisaz

import com.pars.agahisaz.User

class Place {

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
    }

    static mapping = {
        location geoIndex: true
//        index name: 'text', address: 'text', indexAttributes: [name: 10, address: 1, tags: 5]
        compoundIndex province: 1, city: 1, category: 1
        tags index: true
    }

    def beforeUpdate() {
        if(!dateCreated)
            dateCreated = new Date()
    }
}
