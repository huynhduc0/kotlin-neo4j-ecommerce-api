package com.thduc.eshop.entity

import org.springframework.data.annotation.CreatedBy
import java.util.*
import javax.persistence.*


@Entity
class Card(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? =null,
    var cardHolder: String? = null,
    var cardNumber: String? = null,
    var validThrough: Date? = null,
    @OneToOne var user: User?= null,
    var defaultCard: Boolean? = false,
) {
    constructor(): this(null,null,null)
}