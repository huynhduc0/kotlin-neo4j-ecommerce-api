package com.thduc.eshop.repository

import com.thduc.eshop.entity.Product
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: PagingAndSortingRepository<Product, Long> {
}