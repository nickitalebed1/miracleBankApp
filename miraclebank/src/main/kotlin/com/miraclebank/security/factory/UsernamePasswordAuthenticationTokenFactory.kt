package com.miraclebank.security.factory

import com.miraclebank.model.entity.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsernamePasswordAuthenticationTokenFactory {

    fun create(u: User): UsernamePasswordAuthenticationToken {
        val simpleGrantedAuthority = SimpleGrantedAuthority(u.getRole())
        val authentication = UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), Arrays.asList(simpleGrantedAuthority))
        return authentication
    }

}