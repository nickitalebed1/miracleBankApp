package com.miraclebank.security.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {

    @Autowired
    internal var headerHandler: HeaderHandler? = null

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        headerHandler!!.process(request, response)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed")
    }

}
