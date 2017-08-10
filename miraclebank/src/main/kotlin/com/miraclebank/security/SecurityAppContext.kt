package com.miraclebank.security

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityAppContext {

    val context: SecurityContext
        get() = SecurityContextHolder.getContext()

}