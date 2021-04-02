package com.thduc.eshop.exception

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class UnauthorizedEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val expiredMsg = request!!.getAttribute("token")
        val msg: String =
            expiredMsg?.toString() ?: "Your login session has expired"
        response!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg)
    }
}