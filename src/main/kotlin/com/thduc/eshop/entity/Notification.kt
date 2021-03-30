package com.thduc.eshop.entity

import com.thduc.eshop.constant.NotificationType
import java.util.*
import javax.persistence.*

@Entity
data class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String?,
    var content:String?,
    @OneToOne
    var device: Device?,
    var seenDate: Date?=null,
    var seen: Boolean = false,
    var notificationType: NotificationType?,
//    var fromUser: User,
    @OneToOne var toUser: User?,
) {
    constructor(): this(null,null,null,null,null,false,null,null)
}