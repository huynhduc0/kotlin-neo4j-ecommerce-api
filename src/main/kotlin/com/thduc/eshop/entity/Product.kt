package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

import javax.persistence.*


@Entity
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? = null,
    var name:String? = "",
    var shortDescription: String? = "",
    var description: String? = "",
    @ManyToMany
    var categories: Set<Category>? = null,
    var stock: Int? = 0,
    @ManyToMany
    var medias: Set<Media>? = null,
    @OneToOne
    var shop: Shop?=null,
    var status: StatusType? = StatusType.ACTIVATE,
    @OneToOne var user: User ?= null,
    @OneToMany(cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var productProperties: Set<ProductProperty>? = null,
    var totalRating:Int?=0,
    var rating: Double =0.0,
    @CreationTimestamp var created: LocalDateTime? = null,
    @UpdateTimestamp var updated: LocalDateTime? = null
) {
    constructor(): this(null,null,null,null,null,null,null,null,null,null,null)
}