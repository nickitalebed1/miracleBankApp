package com.miraclebank.controller

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class UserController {

    internal val USERNAME = "username"
    internal val AUTHORIZATIONS = "permissions"

    @RequestMapping(value = "/user", method = arrayOf(RequestMethod.GET))
    fun user(request: HttpServletRequest): HttpEntity<Map<*, *>> {
        val result = HashMap<String, Any>()
        val auth = SecurityContextHolder.getContext().authentication
        val authorizations = HashMap<String, Boolean>()
        for (grantedAuthority in auth.authorities) {
            authorizations.put(grantedAuthority.authority, java.lang.Boolean.TRUE)
        }
        result.put(AUTHORIZATIONS, authorizations)
        val username = auth.principal as String
        result.put(USERNAME, username)
        if ("anonymousUser" == username) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        return HttpEntity(result)
    }
}
