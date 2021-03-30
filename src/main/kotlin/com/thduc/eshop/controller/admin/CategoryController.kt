package com.thduc.eshop.controller.admin

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Category
import com.thduc.eshop.request.CategoryForm
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("categories")
class CategoryController(
    @Autowired val categoryService: CategoryService
) {
    @GetMapping
    fun getAllCategory(
        @RequestParam(value = "page",defaultValue = "0")  page: Int,
        @RequestParam(value = "size", defaultValue = "10")  size: Int,
        @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
        @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String,
    ): Page<Category> {
        return if (sortOrder == "desc")
            categoryService.getAll(PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else categoryService.getAll(PageRequest.of(page, size, Sort.by(sortBy)))
    }

    @GetMapping("{id}")
    fun getCategory(@PathVariable id: Long): Category {
        return categoryService.getCategory(id)
    }

    @PostMapping
    fun addCategory(category: CategoryForm,@ActiveUser userPrincipal: UserPrincipal): Category {
        return categoryService.addCategory(userPrincipal.getUser()!!,category)
    }

    @DeleteMapping
    fun delete(@RequestBody category: Category): SuccessActionResponse {
        return categoryService.delete(category)
    }

    @PutMapping("{id}")
    fun edit(@PathVariable id: Long, @RequestBody category: Category): Category {
        return categoryService.edit(id, category)
    }
}