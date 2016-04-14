package com.pars.agahisaz

class User {

    transient springSecurityService

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }
    static mapWith = "mongo"
    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
