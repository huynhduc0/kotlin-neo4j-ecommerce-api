package com.thduc.eshop.entity

import javax.persistence.*


@Entity
data class Rating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?=null,
    var rating: Int?= -1,
    @OneToOne
    var product: Product? = null,
   @OneToOne
   var user:User? = null,
    var content:String? = null,
    @OneToMany(fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    var medias: Set<Media>? = null,
    var systemRating: Int? = -1,
) {
    constructor(): this(null,null,null)
}