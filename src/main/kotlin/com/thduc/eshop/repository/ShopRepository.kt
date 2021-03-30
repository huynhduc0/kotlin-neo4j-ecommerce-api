package com.thduc.eshop.repository

import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository: PagingAndSortingRepository<Shop, String> {
    fun findTopByUser(user:User):Shop
}