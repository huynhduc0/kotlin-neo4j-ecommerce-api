package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import javax.persistence.*

@Entity
class OrderDetail(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @OneToOne
    val product: Product?,
    val unitPrice: Long?,
    val quantity: Int?,
    val status: StatusType? = StatusType.NEW
) {
    constructor(): this(null,null,null,null,null)

}