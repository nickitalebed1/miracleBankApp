package com.miraclebank.security.handler

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class Http401UnauthorizedEntryPoint : AuthenticationEntryPoint {

    @Autowired
    internal var headerHandler: HeaderHandler? = null

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, arg2: AuthenticationException) {
        LOGGER.debug("Pre-authenticated entry point called. Rejecting access:" + request.requestURL)
        headerHandler!!.process(request, response)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint::class.java)
    }
}
