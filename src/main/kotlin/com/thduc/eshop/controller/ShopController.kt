package com.thduc.eshop.controller

import com.thduc.eshop.entity.Shop
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("shop")
//@PreAuthorize("hasRole("+"MERCHANT"+")")
class ShopController {
    @GetMapping("my")
    fun getMyShop():String{
        return "ship"
    }
}