package com.thduc.eshop.controller.merchant

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ShopService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("shop")
@PreAuthorize("hasRole('ROLE_MERCHANT')")
class ShopController(
    @Autowired val shopService: ShopService
) {
    @GetMapping
    fun getMyShop(@ActiveUser userPrincipal: UserPrincipal): Shop? {
        val shop = shopService.findShopByUser(userPrincipal.currentUser)
        return if (shop == null) shop else throw DataNotFoundException("shop","shop",userPrincipal.username)
    }
    @GetMapping("{id}")
    fun getById(@PathVariable id:String, @ActiveUser userPrincipal: UserPrincipal): Shop? {
        val shop = shopService.findShopById(id)
        return if (shop == null) shop else throw DataNotFoundException("shop","shop",userPrincipal.username)
    }
    @PostMapping
    fun setSop(@RequestBody shop: Shop,@ActiveUser userPrincipal: UserPrincipal):Shop{
        return shopService.saveShop(userPrincipal.currentUser!!,shop)
    }
    @PutMapping
    fun updateShop(@RequestBody shop: Shop,@ActiveUser userPrincipal: UserPrincipal):Shop{
        shop.user = userPrincipal.currentUser!!
        return shopService.saveShop(userPrincipal.currentUser!!,shop)
    }
    @DeleteMapping
    fun deleteShop(@ActiveUser userPrincipal: UserPrincipal): SuccessActionResponse{
        return shopService.deleteShop(userPrincipal.currentUser!!)
    }
}