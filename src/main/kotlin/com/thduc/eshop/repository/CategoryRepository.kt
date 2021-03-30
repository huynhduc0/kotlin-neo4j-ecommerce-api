package com.thduc.eshop.repository

import com.thduc.eshop.entity.Category
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository
@Repository
interface CategoryRepository: PagingAndSortingRepository<Category, Long> {
}