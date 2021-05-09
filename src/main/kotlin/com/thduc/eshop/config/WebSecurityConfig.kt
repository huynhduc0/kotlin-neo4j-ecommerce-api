package com.thduc.eshop.config

import com.thduc.eshop.exception.PermissionDenyEntryPoint
import com.thduc.eshop.exception.UnauthorizedEntryPoint
import com.thduc.eshop.service.AppUserDetailsService
import com.thduc.eshop.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val bCryptPasswordEncoder: PasswordEncoder,
    val userDetailsService: AppUserDetailsService,
    @Autowired val userService: UserService,
    private val securityProperty: SecurityProperty
): WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
//        super.configure(http)
        http!!
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no sessions
            .and()
            .authorizeRequests()
//            .antMatchers("/api/**").permitAll()

//            .antMatchers("/error/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers(HttpMethod.POST, "/users/register").permitAll()
            .antMatchers(HttpMethod.POST, "/users/login").permitAll()
            .antMatchers(HttpMethod.POST, "/users/google").permitAll()
            .antMatchers(HttpMethod.GET, "/media/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthorizationFilter(authenticationManager(), securityProperty,userService))
            .addFilter(JWTAuthenticationFilter(authenticationManager(), securityProperty,userService))
            .exceptionHandling().accessDeniedHandler(PermissionDenyEntryPoint()).authenticationEntryPoint(UnauthorizedEntryPoint())
//            .addFilterBefore(JWTAuthenticationFilter(authenticationManager(), securityProperty,userService),UsernamePasswordAuthenticationFilter::class.java)
    }
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider = DaoAuthenticationProvider().apply {
        setUserDetailsService(userDetailsService)
        setPasswordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition"
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}