package com.thduc.eshop.config

import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.IOException
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource


class JWTAuthorizationFilter(
    authManager: AuthenticationManager,
    private val securityProperty: SecurityProperty,
    @Autowired val userService: UserService,
    val logger: org.slf4j.Logger = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)
) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(securityProperty.headerString)
        if (header == null || !header.startsWith(securityProperty.tokenPrefix)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header)?.also {
            it.details = WebAuthenticationDetailsSource().buildDetails(req)
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
         try {
             logger.info(token.replace(securityProperty.tokenPrefix, ""))
             val claims = Jwts.parserBuilder()
                 .setSigningKey(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()))
                 .build()
                 .parseClaimsJws(token.replace(securityProperty.tokenPrefix, ""))
            val username:String? = claims.body.subject
            val user:com.thduc.eshop.entity.User? = userService.findByUsername(username!!)
            val author: MutableList<SimpleGrantedAuthority> = java.util.ArrayList()
            user!!.roles!!.forEach { role -> author.add(SimpleGrantedAuthority("ROLE_"+role.roleName)) }
             return if (user.username != null) {
                 val userDetail: UserDetails = UserPrincipal.build(user)
                 UsernamePasswordAuthenticationToken(
                     userDetail,
                     null, author
                 )
             }else{
                 UsernamePasswordAuthenticationToken(claims.body.subject, null, null)
             }

        } catch (e: Exception) {
            return null
        }
    }
}