package com.thduc.eshop.request

import com.thduc.eshop.entity.Address
import com.thduc.eshop.entity.OrderDetail


class OrderForm(
    var orderDetails: Set<OrderDetail>?,
    var fee: Double? = 0.0,
    var total: Double?= 0.0,
    var shippingAddress: Address?,
) {

}