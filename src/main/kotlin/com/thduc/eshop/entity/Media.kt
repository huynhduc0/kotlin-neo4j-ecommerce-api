package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id

@NodeEntity
class Media(
    @Id @GeneratedValue var id: Long? = null,
    val mediaPath: String? = null,
    val mediaType: String? = null,
    val authorId: Long? = null,
    var status: StatusType?= StatusType.ACTIVATE
//    @CreatedBy val createdBy: User
) {

}