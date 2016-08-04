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

    public transient boolean hasChange(String ignoreField = '') {
        if (this.name != place.name && ignoreField != 'name')
            return true
        if (this.province != place.province && ignoreField != 'province')
            return true
        if (this.city != place.city && ignoreField != 'city')
            return true
        if (this.address != place.address && ignoreField != 'address')
            return true
        if (this.postalCode != place.postalCode && ignoreField != 'postalCode')
            return true
        if (this.category?.id != place.category?.id && ignoreField != 'category')
            return true
        if ((this.location[0] != place.location[0] || this.location[1] != place.location[1]) && ignoreField != 'location')
            return true
        if ((this.tags.findAll { it && it != '' }.any { !place.tags.contains(it) } || place.tags.findAll {
            it && it != ''
        }.any {
            !this.tags.contains(it)
        }) && ignoreField != 'tags')
            return true
        false
    }
}
