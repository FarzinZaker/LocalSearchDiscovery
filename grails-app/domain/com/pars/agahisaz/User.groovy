package com.pars.agahisaz

class User {

    transient springSecurityService

    String username
    String password
    Integer superuserLevel = 0
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static constraints = {
        username blank: false, unique: true
        password blank: false
        superuserLevel nullable: true
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
