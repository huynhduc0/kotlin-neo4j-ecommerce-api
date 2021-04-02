package com.thduc.eshop.repository

import com.thduc.eshop.entity.Cart
import com.thduc.eshop.entity.Product
import com.thduc.eshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository:PagingAndSortingRepository<Cart,Long> {
    fun findCartsByUser(user: User,pageable: Pageable): Page<Cart>
    fun findTopByProductAndUser(product: Product,user: User): Cart?
}