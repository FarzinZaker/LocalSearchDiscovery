package agahisaz.old


class OldUser {

    String fullName
    String email
    String password
    String phoneNumber
    String mobileNumber
    String faxNumber
    String company
    String address
    String website
    Integer charge

    static mapping = {

//        datasource 'sqlServer'

        table 'Users'
        version false

        fullName column: 'FullName'
        email column: 'Email'
        password column: 'Password'
        phoneNumber column: 'PhoneNumber'
        mobileNumber column: 'MobileNumber'
        faxNumber column: 'FaxNumber'
        company column: 'Company'
        address column: 'Address'
        website column: 'Website'
        charge column: 'Charge'
    }

    static constraints = {
    }
}
