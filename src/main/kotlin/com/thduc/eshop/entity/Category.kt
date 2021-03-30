package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import javax.persistence.*


@Entity
class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var name: String? = null,
    @OneToOne(cascade = [CascadeType.PERSIST],fetch = FetchType.EAGER)
    var image: Media? = null,
    var status: StatusType? = StatusType.ACTIVATE
) {

    constructor(): this(null,null,null)
}