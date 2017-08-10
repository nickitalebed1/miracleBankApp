package com.miraclebank.security.filter


import com.miraclebank.security.SecurityAppContext
import com.miraclebank.security.factory.UsernamePasswordAuthenticationTokenFactory
import com.miraclebank.service.UserService
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilter : OncePerRequestFilter() {

    @Autowired
    internal var userService: UserService? = null
    @Autowired
    internal var usernamePasswordAuthenticationTokenFactory: UsernamePasswordAuthenticationTokenFactory? = null
    @Autowired
    internal var securityAppContext: SecurityAppContext? = null


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        var authToken: String? = request.getHeader(AUTHORIZATION)
        if (authToken != null) {
            authToken = String(authToken.substring(BEGIN_INDEX).toByteArray())
            val context = securityAppContext!!.context
            if (context.getAuthentication() == null) {
                val u = userService!!.validateUser(authToken, request.remoteAddr)
                if (u != null) {
                    val authentication = usernamePasswordAuthenticationTokenFactory!!.create(u)
                    context.setAuthentication(authentication)
                }
            }
        }
        chain.doFilter(request, response)
    }

    companion object {
        internal val AUTHORIZATION = "Authorization"
        internal val BEGIN_INDEX = 7
    }

}