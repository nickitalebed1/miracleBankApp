package com.miraclebank.service

import com.miraclebank.model.entity.User
import com.miraclebank.model.factory.UserFactory
import com.miraclebank.repository.UserRepository
import com.miraclebank.security.jwt.JwtService
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.*

@Service
class UserService {
    @Autowired
    internal var userRepository: UserRepository? = null
    @Autowired
    internal var shaPasswordEncoder: ShaPasswordEncoder? = null
    @Autowired
    internal var userFactory: UserFactory? = null
    @Autowired
    internal var jwtService: JwtService? = null


    fun create(username: String, password: String, role: String) {
        val salt = UUID.randomUUID().toString();
        val u = userFactory!!.create(username, shaPasswordEncoder!!.encodePassword(password, salt), salt, role)
        userRepository!!.save(u)
    }

    fun isLoginValid(username: String, pass: String): User? {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(pass)) {
            return null
        }
        val password = String(Base64.decodeBase64(pass.toByteArray()))
        val u = userRepository!!.findByUsername(username) ?: return null
        if (u.getPassword() != shaPasswordEncoder!!.encodePassword(password, u.getSalt())) {
            return null
        }
        return u
    }

    fun findByUsername(username: String): User {
        return userRepository!!.findByUsername(username)
    }

    fun createUserToken(username: String, secret: String): User {
        val token = jwtService!!.createToken(username, secret, getExpirationDate())
        val u = userRepository!!.findByUsername(username)
        u.setToken(token!!)
        return userRepository!!.save(u)
    }

    fun validateUser(token: String, secret: String): User? {
        val username = jwtService!!.getUsername(token, secret)
        if (username != null) {
            val user = userRepository!!.findByUsername(username)
            if (token == user.getToken() && jwtService!!.isValid(token, secret)) {
                return user
            }
        }
        return null
    }


    private fun getExpirationDate(): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_WEEK, 1)
        return c.time
    }
}