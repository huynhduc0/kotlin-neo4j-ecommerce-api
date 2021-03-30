package com.thduc.eshop.controller.merchant

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Product
import com.thduc.eshop.request.ProductForm
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ProductService
import com.thduc.eshop.utilities.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("merchant/products")
@RestController
@PreAuthorize("hasRole('ROLE_MERCHANT')")
class ProductController(
    @Autowired val productService: ProductService,
    @Autowired val fileUtil: FileUtil
) {
    @GetMapping
     fun getMyProduct(@ActiveUser userPrincipal: UserPrincipal,
                      @RequestParam(value = "page",defaultValue = "0")  page: Int,
                      @RequestParam(value = "size", defaultValue = "10")  size: Int,
                      @RequestParam(value = "sortBy", defaultValue = "id")  sortBy: String,
                      @RequestParam(value = "sortOrder", defaultValue = "")  sortOrder: String
    ): Page<Product>{
        return if (sortOrder == "desc")
            productService.loadProductByUser(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy).descending()))
        else productService.loadProductByUser(userPrincipal.getUser()!!,PageRequest.of(page, size, Sort.by(sortBy).descending()))
     }
    @PostMapping
    fun addProduct(@RequestBody productForm: ProductForm,@ActiveUser userPrincipal: UserPrincipal): Product{
        return productService.addProduct(productForm,userPrincipal.currentUser!!)
    }
//    @PutMapping
//    fun getMyProduct(@ActiveUser userPrincipal: UserPrincipal): Page<Product>{
//        return productService.loadProductByUser()
//    }
//    @DeleteMapping
//    fun getMyProduct(@ActiveUser userPrincipal: UserPrincipal): Page<Product>{
//        return productService.loadProductByUser()
//    }
}