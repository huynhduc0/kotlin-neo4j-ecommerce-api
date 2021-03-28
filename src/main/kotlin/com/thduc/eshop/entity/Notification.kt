package com.thduc.eshop.entity

import com.thduc.eshop.constant.NotificationType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import java.util.*
@NodeEntity
class Notification(
    @Id @GeneratedValue
    var id: Long? = null,
    var title: String,
    var content:String,
    var device: Device,
    @CreatedDate
    var created: Date = Date(),
    var seenDate: Date?=null,
    var seen: Boolean = false,
    var notificationType: NotificationType,
//    var fromUser: User,
    @CreatedBy var toUser: User,
) {
}