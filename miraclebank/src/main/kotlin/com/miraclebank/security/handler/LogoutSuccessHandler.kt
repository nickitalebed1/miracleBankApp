package com.miraclebank.security.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogoutSuccessHandler : AbstractAuthenticationTargetUrlRequestHandler(), LogoutSuccessHandler {

    @Autowired
    internal var headerHandler: HeaderHandler? = null

    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
        if (authentication != null && authentication.details != null) {
            try {
                headerHandler!!.process(request, response)
                request.session.invalidate()
                response.status = HttpServletResponse.SC_OK
            } catch (e: Exception) {
                response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            }

        }
    }
}
