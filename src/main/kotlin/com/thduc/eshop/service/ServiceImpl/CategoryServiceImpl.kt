package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Category
import com.thduc.eshop.request.SuccessActionResponse

interface CategoryServiceImpl {
    abstract fun addCategory(category: Category): Category
    abstract fun delete(category: Category): SuccessActionResponse
    abstract fun edit(id: Long, category: Category): Category
}