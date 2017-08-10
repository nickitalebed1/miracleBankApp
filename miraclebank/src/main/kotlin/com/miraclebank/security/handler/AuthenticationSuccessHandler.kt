package com.miraclebank.security.handler

import com.miraclebank.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {

    @Autowired
    internal var headerHandler: HeaderHandler? = null
    @Autowired
    internal var userService: UserService? = null

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        logger.debug("Authentication Successful")
        val u = userService!!.createUserToken(authentication.name, request.remoteAddr)
        response.writer.print("{ \"token\" : \"" + u.getToken() + "\"}")
        response.status = HttpServletResponse.SC_OK
        headerHandler!!.process(request, response)
    }

}