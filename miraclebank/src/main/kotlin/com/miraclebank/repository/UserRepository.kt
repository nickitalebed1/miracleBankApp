package com.miraclebank.repository

import com.miraclebank.model.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

    fun findByUsername(username: String): User

}
