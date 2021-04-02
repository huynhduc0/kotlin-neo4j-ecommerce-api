package com.thduc.eshop.request

import com.thduc.eshop.entity.Product
import com.thduc.eshop.entity.ProductOption
import com.thduc.eshop.entity.ProductProperty

class CartForm(
    var product: Product? = null,
    var quantity: Int,
    var productProperty: ProductProperty? = null,
    var productOption: ProductOption? = null
) {

}