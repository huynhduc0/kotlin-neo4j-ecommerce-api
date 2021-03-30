package com.thduc.eshop.service

import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.Category
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.CategoryRepository
import com.thduc.eshop.request.CategoryForm
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.service.ServiceImpl.CategoryServiceImpl
import com.thduc.eshop.utilities.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.awt.Image

@Service
class CategoryService(
    @Autowired val categoryRepository: CategoryRepository,
    @Autowired val fileUtil: FileUtil
): CategoryServiceImpl {
    fun getAll(pageable: Pageable): Page<Category> {
        return categoryRepository.findAll(pageable)
    }

    fun getCategory(id: Long): Category {
        return categoryRepository.findById(id).orElseThrow{ DataNotFoundException("category","category",id.toString())}
    }

    override fun addCategory(user: User,categoryForm: CategoryForm): Category {
        var image: Media = fileUtil.store(user.username!!,user,categoryForm.image,UploadType.CATEGORY)
        var category: Category = Category(name = categoryForm.name,status = categoryForm.status,image = image)
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