package com.thduc.eshop.service

import com.thduc.eshop.constant.NotificationType
import com.thduc.eshop.entity.*
import com.thduc.eshop.exception.BadRequestException
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.OrderRepository
import com.thduc.eshop.repository.ProductOptionRepository
import com.thduc.eshop.repository.ProductRepository
import com.thduc.eshop.request.OrderForm
import com.thduc.eshop.service.ServiceImpl.OrderServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class OrderService(
    @Autowired val orderRepository: OrderRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val productOptionRepository: ProductOptionRepository,
    @Autowired val notificationService: NotificationService
//    val logger: org.slf4j.Logger = LoggerFactory.getLogger(OrderService::class.java)
) : OrderServiceImpl {
    fun getMerchantOder(user: User, of: PageRequest):Page<Orders>{
        return orderRepository.findAllByShop_User(user,of)
    }
    fun getUserOder(user: User, of: PageRequest):Page<Orders>{
        return orderRepository.findAllByUser(user,of)
    }
    fun createOrder(user: User, orderForm: OrderForm): Orders {
        orderForm.orderDetails!!.forEach {
//            logger.info(it.toString())
            it.product!!.shop = productRepository.findById(it.product!!.id!!)
                .orElseThrow { DataNotFoundException("prod", "prod", it.product.id.toString()) }.shop
        }
        var orders = Orders(
            null, user, orderForm.orderDetails, orderForm.fee, orderForm.total, orderForm.shippingAddress,
            orderForm.orderDetails!!.first().product!!.shop
        )
        orders.orderDetails!!.forEach { orderDetail ->
            val productOption = productOptionRepository.findById(orderDetail!!.productOption!!.id!!).orElseThrow { BadRequestException("This product option is no logger support") }
            subProduct(orderDetail.product!!,orderDetail.productProperty,productOption,orderDetail.quantity!!)
        }
        val order = orderRepository.save(orders)
        val product = orderForm.orderDetails!!.first().product!!.id?.let { productRepository.findById(it).orElseThrow { DataNotFoundException("<","<","<") } }
        notificationService.addNotification(user,"Your order is ready right now, thank for choosing our service","Order successful",NotificationType.ORDER,order.id!!, product!!.medias!!.first().mediaPath)
        return order
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

    override fun changeStatus(currentUser: User, id: Long, orders: Orders): Orders {
        var oldOrder = orderRepository.findById(id).orElseThrow { DataNotFoundException("order","id",id.toString()) }
        oldOrder.status = orders.status
        val order =  orderRepository.save(oldOrder)
        notificationService.addNotification(order.user,"Your order now is ${order.status}, check it now!","Your order has been ${order.status}",NotificationType.ORDER,order.id!!, order.orderDetails!!.first().product!!.medias!!.first().mediaPath)
        return order
    }
}