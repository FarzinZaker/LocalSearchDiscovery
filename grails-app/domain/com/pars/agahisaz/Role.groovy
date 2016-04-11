package com.pars.agahisaz

class Role {

	String authority

	static mapping = {
		cache true
	}
	static mapWith = "mongo"
	static constraints = {
		authority blank: false, unique: true
	}
}
