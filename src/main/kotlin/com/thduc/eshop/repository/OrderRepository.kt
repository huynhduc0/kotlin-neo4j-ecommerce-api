package com.thduc.eshop.repository

import com.thduc.eshop.entity.Orders
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: PagingAndSortingRepository<Orders, Long> {
}