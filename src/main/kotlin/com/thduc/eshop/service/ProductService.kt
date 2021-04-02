package com.thduc.eshop.service

import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.Product
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.BadRequestException
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.ProductRepository
import com.thduc.eshop.request.ProductForm
import com.thduc.eshop.service.ServiceImpl.ProductServiceImpl
import com.thduc.eshop.utilities.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    @Autowired val productRepository: ProductRepository,
    @Autowired val shopService: ShopService,
    @Autowired val fileUtil: FileUtil
) : ProductServiceImpl {
    override fun loadProductByUser(user: User, pageable: Pageable): Page<Product> {
        return productRepository.findAll(pageable)
    }

    override fun addProduct(productForm: ProductForm, user: User): Product {
        val shop: Shop = shopService.findShopByUser(user) ?: throw BadRequestException("You need create shop first!")
        return productRepository.save(
            Product(
                productProperties = productForm.properties!!,
                status = productForm.status,
                medias = productForm.medias,
                name = productForm.name,
                categories = productForm.categories,
                user = user,
                description = productForm.description,
                shop = shop,
                shortDescription = productForm.shortDescription,
                stock = productForm.stock
            )
        )
    }

    override fun edit(id: Long, productForm: ProductForm, user: User): Product {
        var oldProduct =
            productRepository.findById(id).orElseThrow { throw DataNotFoundException("product", "id", id.toString()) }
        oldProduct.productProperties =
            if (productForm.properties != null) productForm.properties else oldProduct.productProperties
        oldProduct.status = if (productForm.status != null) productForm.status else oldProduct.status
        oldProduct.medias = if (productForm.medias != null) productForm.medias else oldProduct.medias
        oldProduct.name = if (productForm.name.isNullOrBlank()) productForm.name else oldProduct.name
        oldProduct.categories = if (productForm.categories != null) productForm.categories else oldProduct.categories
        oldProduct.user = user
        oldProduct.description =
            if (productForm.description != null) productForm.description else productForm.description
        oldProduct.shop = oldProduct.shop
        oldProduct.shortDescription =
            if (productForm.shortDescription != null) productForm.shortDescription else productForm.shortDescription
        oldProduct.stock = if (productForm.stock != null) productForm.stock else oldProduct.stock
        return productRepository.save(
            oldProduct
        )
    }
    override fun deleteProduct(id: Long){
        var oldProduct =
            productRepository.findById(id).orElseThrow { throw DataNotFoundException("product", "id", id.toString()) }
        productRepository.delete(oldProduct)
    }
}