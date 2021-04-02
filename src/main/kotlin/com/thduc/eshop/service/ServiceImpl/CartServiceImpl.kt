package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Cart
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.CartForm
import org.springframework.data.domain.Pageable


interface CartServiceImpl {
    abstract fun getAllByUser(currentUser: User, of: Pageable): Any
    abstract fun addToCart(currentUser: User, cartForm: CartForm): Cart
    abstract fun updateQuantity(currentUser: User?, id: Long, cartForm: CartForm): Cart
    abstract fun delete(currentUser: User?, id: Long): Boolean
}