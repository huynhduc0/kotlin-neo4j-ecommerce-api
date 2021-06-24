package com.thduc.eshop.repository

import com.thduc.eshop.entity.Product
import com.thduc.eshop.entity.Rating
import com.thduc.eshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface RatingRepository:PagingAndSortingRepository<Rating,Long> {
    fun findAllByProduct(product: Product,pageable: Pageable):Page<Rating>
    fun findAllByProduct(product: Product):List<Rating>
    fun findAllByProduct_User(user:User,pageable: Pageable):Page<Rating>
}