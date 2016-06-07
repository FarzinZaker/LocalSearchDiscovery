package com.pars.agahisaz

import agahisaz.ActionInstance

class User {

    transient springSecurityService

    String username
    String password
    Integer superuserLevel = 0
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Long totalScore = 0
    Long weekScore = 0

    static hasMany = [actions: ActionInstance]

    static constraints = {
        username blank: false, unique: true
        password blank: false
        superuserLevel nullable: true
    }
    static mapWith = "mongo"
    static mapping = {
        password column: '`password`'
        actions lazy: true
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }

    public transient String getFullName(){
        "${this.firstName} ${this.lastName}"
    }
}
