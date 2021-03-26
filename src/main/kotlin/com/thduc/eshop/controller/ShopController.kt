package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("shop")
//@PreAuthorize("hasRole('MERCHANT')")
class ShopController(
    @Autowired val shopService: ShopService
) {
    @GetMapping("my")
    fun getMyShop(@ActiveUser userPrincipal: UserPrincipal): UserPrincipal {
        return userPrincipal
//        return shopService.findShopByUser(userPrincipal.getUser())
    }
}