package agahisaz

import com.pars.agahisaz.User

class EditSuggestion {

    static mapWith = "mongo"

    //basic info
    String name
    String city
    String province
    String address
    String phone
    String postalCode
    Category category
    List location
    List tags

    //report fields
    String reportType
    String reportComment

    Place place
    User creator

    static constraints = {
        name blank: false
        address nullable: true
        phone nullable: true
        postalCode nullable: true
        reportType nullable: true
        reportComment nullable: true
    }
}
