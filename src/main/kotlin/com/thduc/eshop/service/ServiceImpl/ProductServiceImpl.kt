package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Product
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.ProductForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductServiceImpl {
    fun loadProductByUser(user: User, pageable: Pageable): Page<Product>
    fun addProduct(productForm: ProductForm, user: User): Product
    fun edit(id: Long, productForm: ProductForm, user: User): Product
    fun deleteProduct(id: Long)
    fun loadUserProduct(user: User, pageable: Pageable): Page<Product>
    fun loadRecomendProduct(user: User, pageable: Pageable): Page<Product>
    abstract fun loadProductByUser(user: User, pageable: Pageable, categoryId: String, search: String, shopId:String): Page<Product>
    abstract fun loadProductByUser(currentUser: User, pageable: Pageable, search: String,shopId:String): Page<Product>


}