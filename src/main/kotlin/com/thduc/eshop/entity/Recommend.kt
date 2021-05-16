package com.thduc.eshop.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Recommend(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?=null,
    var userId: Long? = null,
    var productId: Long? = null,
    var num: Int?=null,
) {
}