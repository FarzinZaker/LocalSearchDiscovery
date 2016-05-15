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
    List rates
    Double averageRate
    Integer ratesCount

    static constraints = {
        name blank: false
        address nullable: true
        phone nullable: true
        postalCode nullable: true
        editSuggestion nullable: true
        averageRate nullable: true
        ratesCount nullable: true
    }

    static mapping = {
        location geoIndex: true
//        index name: 'text', address: 'text', indexAttributes: [name: 10, address: 1, tags: 5]
        compoundIndex province: 1, city: 1, category: 1
        tags index: true
    }
}
