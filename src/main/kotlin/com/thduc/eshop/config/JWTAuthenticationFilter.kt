package com.thduc.eshop.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.IOException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource


class JWTAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val securityProperty: SecurityProperty,
    @Autowired val userService: UserService
) : UsernamePasswordAuthenticationFilter() {
    //    @Throws(AuthenticationException::class)
//    override fun attemptAuthentication(
//        req: HttpServletRequest,
//        res: HttpServletResponse?
//    ): Authentication {
//        return try {
//            val mapper = jacksonObjectMapper()
//
//            val creds = mapper
//                .readValue<com.thduc.eshop.entity.User>(req.inputStream)
//
//            authManager.authenticate(
//                UsernamePasswordAuthenticationToken(
//                    creds.username,
//                    creds.password,
//                    ArrayList<GrantedAuthority>()
//                )
//            )
//        } catch (e: IOException) {
//            throw AuthenticationServiceException(e.message)
//        }
//    }
//
//    @Throws(IOException::class, ServletException::class)
//    override fun attemptAuthentication(
//        req: HttpServletRequest,
//        res: HttpServletResponse,
////        chain: FilterChain
//    ) {
//        val user:com.thduc.eshop.entity.User = userService.findByUsername()
//        val header = req.getHeader(securityProperty.headerString)
//        if (header == null || !header.startsWith(securityProperty.tokenPrefix)) {
////            chain.doFilter(req, res)
////            return null
//        }
//        getAuthentication(header)?.also {
//            SecurityContextHolder.getContext().authentication = it
//        }
////        chain.doFilter(req, res)
//    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()))
                .build()
                .parseClaimsJws(token.replace(securityProperty.tokenPrefix, ""))
            val authorities = ArrayList<GrantedAuthority>()
            (claims.body["auth"].toString().split(",")).forEach { role -> authorities.add(SimpleGrantedAuthority(role)) }

            UsernamePasswordAuthenticationToken(claims.body.subject, null, authorities)
        } catch (e: Exception) {
            return null
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val claims: MutableList<String> = mutableListOf()
        if (auth.authorities.isNotEmpty())
            auth.authorities.forEach { a -> claims.add(a.toString()) }
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, securityProperty.expirationTime)
        val token = Jwts.builder()
            .setSubject((auth.principal as User).username)
            .claim("auth", claims)
            .setExpiration(calendar.time)
            .signWith(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
        res.addHeader(securityProperty.headerString, securityProperty.tokenPrefix + token)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val authToken: String?
        val httpRequest = request as HttpServletRequest
        try {
            authToken = httpRequest.getHeader(securityProperty.headerString)
            val responses = response as HttpServletResponse
            responses.setHeader("Access-Control-Allow-Origin", "*");
            responses.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
            responses.setHeader("Access-Control-Allow-Headers", "*")
            val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()))
                .build()
                .parseClaimsJws(authToken.replace(securityProperty.tokenPrefix, ""))


            val username: String = claims.body.subject
            var user: com.thduc.eshop.entity.User = userService.findByUsername(username)
            if (user.username != null) {
                val userDetail: UserPrincipal = UserPrincipal.build(user)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetail,
                    null,
                    userDetail.grantedAuthorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)
                SecurityContextHolder.getContext().authentication = authentication
                chain!!.doFilter(request, response);
            }
        } catch (ex: ExpiredJwtException) {
            request.setAttribute("token", "Login session expires");
//            tokenService.deleteByValue(deleteByValueauthToken);
        } catch (ex: MalformedJwtException) {
            request.setAttribute("token", "Invalid token fomat");
        } catch (ex: SignatureException) {
            request.setAttribute("token", "Invalid token");
        } catch (ex: NullPointerException) {
            request.setAttribute("token", "Invalid user");
        } catch (ex: Exception) {
            request.setAttribute("token", "Invalid user");
        }
//        UsernamePasswordAuthenticationToken(claims.body.subject, null, authorities)
    }


}
