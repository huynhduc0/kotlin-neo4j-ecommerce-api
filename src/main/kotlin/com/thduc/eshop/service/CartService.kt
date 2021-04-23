package com.thduc.eshop.service

import com.thduc.eshop.entity.*
import com.thduc.eshop.exception.BadRequestException
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.CartRepository
import com.thduc.eshop.repository.ProductOptionRepository
import com.thduc.eshop.repository.ProductRepository
import com.thduc.eshop.request.CartForm
import com.thduc.eshop.service.ServiceImpl.CartServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CartService(
    @Autowired val cartRepository: CartRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val productOptionRepository: ProductOptionRepository,
) : CartServiceImpl {

    override fun getAllByUser(currentUser: User, of: Pageable): Page<Cart> {
        return cartRepository.findCartsByUser(currentUser, of)
    }

    override fun addToCart(currentUser: User, cartForm: CartForm): Cart {
//        if(1+1 == 2)  throw BadRequestException("Out of stock")
        val product: Product = productRepository.findById(cartForm.product!!.id!!)
            .orElseThrow { DataNotFoundException("Product", "id", cartForm.product!!.id.toString()) }
        if (cartForm.productOption == null && product.stock!! < cartForm.quantity)
            throw BadRequestException("Out of stock")
        var cart: Cart? = if(cartForm.productOption!=null) cartRepository.findTopByProductAndUserAndProductProperty_IdAndProductOption_Id(product,currentUser,cartForm.productProperty!!.id!!,cartForm.productOption!!.id!!)
        else cartRepository.findTopByProductAndUser(product, currentUser)
        val flag: Boolean = cart == null
        cart = cart ?: Cart(
            product = product,
            user = currentUser
        )

        if (cartForm.productOption == null && (if (flag) cartForm.quantity else cartForm.quantity + cart.quantity!!) <= product.stock!!) {
            cart.quantity = cart.quantity?.plus(1)
            return cartRepository.save(cart)
        } else if(cartForm.productOption == null ) {
            throw BadRequestException("Out of stock")
        }
        val productProperty = productProperty(product, cartForm)
        if (!flag) cart.quantity = cart.quantity?.plus(cartForm.quantity)
        return cartRepository.save(
            if (flag)
                Cart(
                    product = product,
                    user = currentUser,
                    quantity = cartForm.quantity,
                    productProperty = productProperty,
                    productOption = productProperty.options!!.first { productOption -> productOption.id == cartForm.productOption!!.id }
                ) else cart
        )
    }

    override fun updateQuantity(currentUser: User?, id: Long, cartForm: CartForm): Cart {

        val cart: Cart? = cartRepository.findById(id)
            .orElseThrow { throw BadRequestException("Your cart for this product not found") }

        val product: Product = cart!!.product!!.id?.let {
            productRepository.findById(it).orElseThrow { DataNotFoundException("Product", "id", id.toString()) }
        }!!
        if (cartForm.productOption == null && product.stock!! < cartForm.quantity)
            throw BadRequestException("Out of stock")
        val productProperty =
            productProperty(product, cartForm)
        cart.productProperty = productProperty
        cart.productOption =
            productProperty.options!!.first { productOption -> productOption.id == cartForm.productOption!!.id }
        cart.quantity = cartForm!!.quantity
        return cartRepository.save(cart)
    }

    private fun productProperty(
        product: Product,
        cartForm: CartForm
    ): ProductProperty {
        val productProperty =
            product.productProperties!!.first { productProperty -> productProperty.id == cartForm.productProperty!!.id }
        val productOption =
            productProperty.options!!.first { productOption -> productOption.id == cartForm.productOption!!.id }
        if (!(productOption.id == productOption.id && productProperty.id == productProperty.id && productOption.subQuantity!! >= cartForm.quantity)) {
            throw BadRequestException("Out of stock")
        }
        return productProperty
    }

    override fun delete(currentUser: User?, id: Long): Boolean {
        val cart: Cart? = cartRepository.findById(id)
            .orElseThrow { throw BadRequestException("Your cart for this product not found") }
        cartRepository.delete(cart!!)
        return true
    }

    fun subProduct(
        product: Product,
        productProperty: ProductProperty?,
        productOption: ProductOption?,
        quantity: Int
    ): Boolean {
        if (productOption == null && product.stock!! < quantity) {
            throw BadRequestException("Out of stock")
            return false
        } else if (productOption == null) {
            product.stock!!.minus(quantity)
            productRepository.save(product)
        }
        if (productOption!!.id == productOption!!.id && productProperty!!.id == productProperty!!.id && productOption.subQuantity!! >= quantity) {
            var productOption1 = productOption.id?.let {
                productOptionRepository.findById(it)
                    .orElseThrow { DataNotFoundException("option", "name", productOption.name) }
            }
            productOption1!!.subQuantity = productOption1!!.subQuantity?.minus(quantity)
            productOptionRepository.save(productOption1)
        } else {
            throw BadRequestException("Out of stock")
            return false
        }
        return true
    }

}