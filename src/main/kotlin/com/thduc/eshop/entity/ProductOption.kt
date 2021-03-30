package com.thduc.eshop.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class ProductOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? = null,
    var name: String,
    var value: String
) {
    constructor(): this(name="noname",value = "nothing")
}