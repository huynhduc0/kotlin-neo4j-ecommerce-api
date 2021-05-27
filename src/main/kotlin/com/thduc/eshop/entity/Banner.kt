package com.thduc.eshop.entity

import com.thduc.eshop.constant.BannerType
import com.thduc.eshop.constant.StatusType
import javax.persistence.*

@Entity
class Banner(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? = null,
    @OneToOne
    var media: Media? = null,
    var text: String? = null,
    var destinationId: Long? = null,
    var type:BannerType? = BannerType.HOME,
    var status:StatusType? = StatusType.ACTIVATE
    ) {
    constructor():this(null)
}