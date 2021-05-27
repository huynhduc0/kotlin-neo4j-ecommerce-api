package com.thduc.eshop.controller.admin

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Banner
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.BannerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("banner")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class BannerController(
    @Autowired val bannerService: BannerService
) {

    @GetMapping()
    fun getAllBanner():Set<Banner>{
        return bannerService.getAllBanner()
    }
    @PostMapping
    fun addBanner(@RequestBody banner: Banner, @ActiveUser userPrincipal: UserPrincipal): Banner {
        return bannerService.saveBanner(banner)
    }
//    @PutMapping
//    fun updateShop(@RequestBody shop: Shop, @ActiveUser userPrincipal: UserPrincipal): Shop {
//        shop.user = userPrincipal.currentUser!!
//        return shopService.saveShop(userPrincipal.currentUser!!,shop)
//    }
    @DeleteMapping("{id}")
    fun deleteBanner(@ActiveUser userPrincipal: UserPrincipal,@PathVariable id:Long): SuccessActionResponse {
        return bannerService.deleteShop(id)
    }
}