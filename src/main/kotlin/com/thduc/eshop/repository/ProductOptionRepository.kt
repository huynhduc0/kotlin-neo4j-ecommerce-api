package com.thduc.eshop.repository

import com.thduc.eshop.entity.ProductOption
import org.springframework.data.repository.PagingAndSortingRepository

interface ProductOptionRepository: PagingAndSortingRepository<ProductOption,Long> {

}
