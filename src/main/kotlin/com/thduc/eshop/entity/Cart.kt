package com.thduc.eshop.entity

import javax.persistence.*

@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @OneToOne
    var user: User? = null,
    @ManyToOne(fetch = FetchType.LAZY,cascade = [CascadeType.MERGE])
    var product: Product? = null,
    var quantity: Int? = 0,
    @ManyToOne
    var productProperty: ProductProperty? = null,
    @ManyToOne
    var productOption: ProductOption? = null,
) {


}