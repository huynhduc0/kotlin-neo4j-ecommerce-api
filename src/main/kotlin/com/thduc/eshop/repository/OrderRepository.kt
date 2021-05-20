package com.thduc.eshop.repository

import com.thduc.eshop.entity.Orders
import com.thduc.eshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: PagingAndSortingRepository<Orders, Long> {
    fun findAllByShop_User(user:User,pageable: Pageable):Page<Orders>
    fun findAllByUser(user:User,pageable: Pageable):Page<Orders>
}