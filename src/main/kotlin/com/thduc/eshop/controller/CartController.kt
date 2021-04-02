package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Cart
import com.thduc.eshop.request.CartForm
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RequestMapping("cart")
@RestController
class CartController(
    @Autowired val cartService: CartService
) {
    @GetMapping
    fun getByUser(
        @ActiveUser userPrincipal: UserPrincipal,
        @RequestParam(value = "page",defaultValue = "0")  page: Int,
        @RequestParam(value = "size", defaultValue = "10")  size: Int,
        @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
        @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ):Page<Cart>{
        return if (sortOrder == "desc")
            cartService.getAllByUser(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else cartService.getAllByUser(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @PostMapping
    fun addProductToCart(
        @ActiveUser userPrincipal: UserPrincipal,
        @RequestBody cartForm: CartForm
    ):Cart{
        return cartService.addToCart(userPrincipal.currentUser!!, cartForm)
    }
    @PutMapping("{id}")
    fun updateQuantity(
        @ActiveUser userPrincipal: UserPrincipal,
        @PathVariable id:Long,
        @RequestBody cartForm: CartForm
    ):Cart{
        return cartService.updateQuantity(userPrincipal.currentUser, id, cartForm)
    }
    @DeleteMapping("{id}")
    fun delete(
        @ActiveUser userPrincipal: UserPrincipal,
        @PathVariable id:Long,
    ):SuccessActionResponse{
        return SuccessActionResponse("delete",cartService.delete(userPrincipal.currentUser, id))
    }
}