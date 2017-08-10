package com.miraclebank.model.factory

import com.miraclebank.model.entity.User
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun create(username: String, password: String, salt: String, role: String): User {
        return User(username, password, salt, role)
    }
}