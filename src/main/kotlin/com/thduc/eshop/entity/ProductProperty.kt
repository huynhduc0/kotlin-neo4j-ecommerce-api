package com.thduc.eshop.entity

import javax.persistence.*


@Entity
class ProductProperty(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? = null,
    var propertyName: String? = null,
    @OneToMany(cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var options: Set<ProductOption>? = null
) {
    constructor(): this(null,null,null)
}