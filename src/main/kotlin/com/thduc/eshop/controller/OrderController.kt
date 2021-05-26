package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.annotation.LogExecution
import com.thduc.eshop.entity.Orders
import com.thduc.eshop.request.OrderForm
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional

@RequestMapping("order")
@RestController
class OrderController(
    @Autowired val orderService: OrderService
) {
    @PostMapping
    @Transactional
    @LogExecution
    fun createOrder(@RequestBody orderForm: OrderForm ,@ActiveUser userPrincipal: UserPrincipal): Orders {
        return orderService.createOrder(userPrincipal.currentUser!!,orderForm)
    }
    @GetMapping
    fun getAllOrder(@ActiveUser userPrincipal: UserPrincipal,
                    @RequestParam(value = "page", defaultValue = "0") page: Int,
                    @RequestParam(value = "size", defaultValue = "10") size: Int,
                    @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                    @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String): Page<Orders> {
        return if (sortOrder == "desc")
            orderService.getUserOder(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else orderService.getUserOder(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
}