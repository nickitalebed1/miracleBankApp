package com.miraclebank.model.entity

import javax.persistence.*

@Entity
class User() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    internal var id: Int? = null
    @Column(nullable = false, unique = true)
    internal var username: String = ""
    @Column(nullable = false)
    internal var password: String = ""
    @Column(nullable = false)
    internal var salt: String = ""
    @Column(nullable = false)
    internal var role: String = ""
    @Column
    internal var token: String = ""

    constructor(username: String, password: String, salt: String, role: String) : this() {
        this.username = username
        this.password = password
        this.salt = salt
        this.role = role
    }

    fun getId(): Int? {
        return id
    }

    fun getUsername(): String {
        return username
    }

    fun getPassword(): String {
        return password
    }

    fun getSalt(): String {
        return salt
    }

    fun getRole(): String {
        return role
    }

    fun getToken(): String {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }
}