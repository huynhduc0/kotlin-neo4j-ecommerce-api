package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import javax.persistence.*


@Entity
class Media(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    val mediaPath: String? = null,
    val mediaType: String? = null,
    var status: StatusType?= StatusType.ACTIVATE,
    @OneToOne
    val createdBy: User? = null
) {
    constructor(): this(null,null,null)

}