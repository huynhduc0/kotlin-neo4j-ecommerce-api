package com.thduc.eshop.entity

import com.thduc.eshop.entity.*
import javax.persistence.*

@Entity
class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @OneToOne var user: User?,
   @OneToMany
    var orderDetails: Set<OrderDetail>?,
    var fee: Double? = 0.0,
    var total: Double?= 0.0,
  @OneToOne
    var shippingAddress: Address?,
   @OneToOne
    var shop: Shop? = null
) {
    constructor(): this(null,null,null,null,null,null)
}