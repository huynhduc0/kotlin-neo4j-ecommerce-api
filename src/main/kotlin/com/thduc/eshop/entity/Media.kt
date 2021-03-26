package com.thduc.eshop.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Media(
    @Id @GeneratedValue var id: Long? = null,
    val mediaPath: String? = null,
    val mediaType: String? = null,
    val authorId: Long? = null,
//    @CreatedBy val createdBy: User
) {

}