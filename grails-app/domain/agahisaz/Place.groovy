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
    User creator

    static constraints = {
        name blank: false
        address nullable: true
        phone nullable: true
        postalCode nullable: true
    }
}
