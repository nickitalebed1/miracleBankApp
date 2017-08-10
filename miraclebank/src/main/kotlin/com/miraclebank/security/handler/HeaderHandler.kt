package com.miraclebank.security.handler

import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

@Component
class HeaderHandler {

    @Throws(IOException::class)
    fun process(request: HttpServletRequest, response: HttpServletResponse) {
        response.setHeader(ALLOW_ORIGIN, STAR)
        response.setHeader(ALLOW_CREDENTIALS, TRUE)
        response.setHeader(ALLOW_HEADERS, request.getHeader(REQUEST_HEADERS))
        if (request.method == OPTIONS) {
            response.writer.print(OK)
            response.writer.flush()
        }
    }

    companion object {
        internal val ALLOW_ORIGIN = "Access-Control-Allow-Origin"
        internal val ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials"
        internal val ALLOW_HEADERS = "Access-Control-Allow-Headers"
        internal val OPTIONS = "OPTIONS"
        internal val OK = "OK"
        internal val REQUEST_HEADERS = "Access-Control-Request-Headers"
        internal val STAR = "*"
        internal val TRUE = "true"
    }
}
