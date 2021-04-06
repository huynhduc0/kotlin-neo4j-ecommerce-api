package com.thduc.eshop.threePartyService

import com.stripe.Stripe
import com.stripe.exception.AuthenticationException
import com.stripe.exception.CardException
import com.stripe.exception.InvalidRequestException
import com.stripe.model.Charge
import com.thduc.eshop.request.ChargeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.HashMap
import javax.annotation.PostConstruct


@Service
class StripeService(

) {
    @Value("\${stripe.api.published-key}")
    lateinit var stripePublishedApi: String

    @PostConstruct
    fun init() {
        Stripe.apiKey = stripePublishedApi
    }

    @Throws(
        AuthenticationException::class,
        InvalidRequestException::class,
        CardException::class,
    )
    fun charge(chargeRequest: ChargeRequest): Charge? {
        val chargeParams: MutableMap<String, Any> = HashMap()
        chargeParams["amount"] = chargeRequest.amount as Any
        chargeParams["currency"] = chargeRequest.currency as Any
        chargeParams["description"] = chargeRequest.description as Any
        chargeParams["source"] = chargeRequest.stripeToken as Any
        return Charge.create(chargeParams)
    }
}