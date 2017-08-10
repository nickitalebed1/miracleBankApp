package com.miraclebank.security

import com.miraclebank.security.factory.UsernamePasswordAuthenticationTokenFactory
import com.miraclebank.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class AuthProviderService : AuthenticationProvider {

    @Autowired
    internal var userService: UserService? = null
    @Autowired
    internal var usernamePasswordAuthenticationTokenFactory: UsernamePasswordAuthenticationTokenFactory? = null

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val login = authentication.name
        val password = authentication.credentials.toString()
        LOGGER.info("Doing login " + login)
        val u = userService!!.isLoginValid(login, password)
        if (u != null) {
            LOGGER.info("Login successful. User: " + login)
            return usernamePasswordAuthenticationTokenFactory!!.create(u)
        }
        throw UsernameNotFoundException("Not valid login/password")
    }

    override fun supports(aClass: Class<*>): Boolean {
        return aClass == UsernamePasswordAuthenticationToken::class.java
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthProviderService::class.java)
    }
}
