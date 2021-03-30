package com.thduc.eshop.entity

import javax.persistence.*


@Entity
data class Rating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?=null,
    var star: Int?= -1,
    @OneToOne
    var product: Product? = null,
   @OneToOne
   var user:User? = null
) {
    constructor(): this(null,null,null)
}