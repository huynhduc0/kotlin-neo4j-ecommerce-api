package com.thduc.eshop.controller.merchant

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("shop")
@PreAuthorize("hasRole('ROLE_MERCHANT')")
class ShopController(
    @Autowired val shopService: ShopService
) {
    @GetMapping
    fun getMyShop(@ActiveUser userPrincipal: UserPrincipal): Shop {
        return shopService.findShopByUser(userPrincipal.getUser())
    }
    @PostMapping
    fun setSop(@RequestBody shop: Shop,@ActiveUser userPrincipal: UserPrincipal):Shop{
        return shopService.saveShop(userPrincipal.getUser()!!,shop)
    }
    @PutMapping
    fun updateShop(@RequestBody shop: Shop,@ActiveUser userPrincipal: UserPrincipal):Shop{
        shop.createdBy = userPrincipal.getUser()!!
        return shopService.saveShop(userPrincipal.getUser()!!,shop)
    }
    @DeleteMapping
    fun deleteShop(@ActiveUser userPrincipal: UserPrincipal): SuccessActionResponse{
        return shopService.deleteShop(userPrincipal.getUser()!!)
    }
}