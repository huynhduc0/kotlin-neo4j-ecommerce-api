package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Category
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.CategoryForm
import com.thduc.eshop.request.SuccessActionResponse

interface CategoryServiceImpl {
     fun delete(category: Category): SuccessActionResponse
     fun edit(id: Long, category: Category): Category
    fun addCategory(user: User, categoryForm: CategoryForm): Category
}