package com.thduc.eshop.service

import com.thduc.eshop.entity.Category
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.CategoryRepository
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.service.ServiceImpl.CategoryServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
    @Autowired val categoryRepository: CategoryRepository
): CategoryServiceImpl {
    fun getAll(pageable: Pageable): Page<Category> {
        return categoryRepository.findAll(pageable)
    }

    fun getCategory(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow{ DataNotFoundException("category","category",id.toString())}
    }

    override fun addCategory(category: Category): Category {
        return categoryRepository.save(category)
    }

    override fun delete(category: Category): SuccessActionResponse {
         categoryRepository.delete(category)
        return SuccessActionResponse("delete",true)
    }

    override fun edit(id: Long, category: Category): Category {
        var old:Category = categoryRepository.findById(id).orElseThrow{ DataNotFoundException("category","category",id.toString())}
        category.id = old.id
        return categoryRepository.save(category)
    }
}