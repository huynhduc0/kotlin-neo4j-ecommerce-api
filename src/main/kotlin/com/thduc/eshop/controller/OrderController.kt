package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Orders
import com.thduc.eshop.request.OrderForm
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RequestMapping("order")
@RestController
class OrderController(
    @Autowired val orderService: OrderService
) {
    @PostMapping
    @Transactional
    fun createOrder(@RequestBody orderForm: OrderForm ,@ActiveUser userPrincipal: UserPrincipal): Orders {
        return orderService.createOrder(userPrincipal.currentUser!!,orderForm)
    }
}