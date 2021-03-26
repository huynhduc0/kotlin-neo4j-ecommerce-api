package com.thduc.eshop.config

import org.hibernate.validator.constraints.Length
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

//@ConfigurationProperties(prefix = "jwt-security")
@Component
class SecurityProperty {
    @Length(min = 42, message = "Minimum length for the secret is 42.")
    var secret = "bexuanmikelontonluondandaucuocvo"
    var expirationTime: Int = 31
    var tokenPrefix = "Bearer "
    var headerString = "Authorization"
    var strength = 10
}