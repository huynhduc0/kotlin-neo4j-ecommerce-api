package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.annotation.LogExecution
import com.thduc.eshop.entity.Banner
import com.thduc.eshop.entity.Category
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.UserForm
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.request.UserResponse
import com.thduc.eshop.service.BannerService
import com.thduc.eshop.service.CategoryService
import com.thduc.eshop.service.ShopService
import com.thduc.eshop.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")

class UserController(@Autowired val userService: UserService,
                     @Autowired val categoryService: CategoryService,
                     @Autowired val bannerService: BannerService,
                     @Autowired val shopService: ShopService) {
    @PostMapping("register")
    @Transactional
    fun register(userForm: UserForm):User{
        return userService.createUser(userForm)
    }

    @PostMapping("login")
    @Transactional

    fun login(@RequestBody userForm: UserForm):UserResponse{
        return userService.login(userForm)
    }
    @PostMapping("google")
    @Transactional
    @LogExecution
    fun googleLogin(userForm: UserForm):UserResponse?{
        return userService.googleLogin(userForm)
    }
    @GetMapping("all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun getAllUser(
        @ActiveUser userPrincipal: UserPrincipal,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
        @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ):Page<User>{
        return if (sortOrder == "desc")
            userService.getAllUser(userPrincipal.currentUser!!,  PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else  userService.getAllUser(userPrincipal.currentUser!!,  PageRequest.of(page, size, Sort.by(sortBy).descending()))
    }
    @GetMapping("shop")
    fun getAllShop(@ActiveUser userPrincipal: UserPrincipal,
                   @RequestParam(value = "page", defaultValue = "0") page: Int,
                   @RequestParam(value = "size", defaultValue = "10") size: Int,
                   @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                   @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ):Page<Shop>{
        return if (sortOrder == "desc")
            shopService.getAllShop(userPrincipal.currentUser!!,  PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else  shopService.getAllShop(userPrincipal.currentUser!!,  PageRequest.of(page, size, Sort.by(sortBy).descending()))

    }
    @GetMapping("me")
    fun me(@ActiveUser userPrincipal: UserPrincipal):User{
        return userPrincipal.currentUser!!
    }
    @GetMapping("categories")
    fun getAllActivateCategories():Set<Category>{
        return categoryService.allActive()
    }
    @GetMapping("banners")
    fun getAllActivateBanners():Set<Banner>{
        return bannerService.getAllActivateBanner()
    }

}