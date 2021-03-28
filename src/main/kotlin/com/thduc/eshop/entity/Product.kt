package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship

@NodeEntity
class Product(
    @Id @GeneratedValue var Id:Long?,
    var name:String?,
    var shortDescription: String?,
    var description: String,
    @Relationship(value = "IS_CATEGORIES",direction = Relationship.Direction.INCOMING)
    var categories: Set<Category>,
    var stock: Int?,
    @Relationship(value = "HAS_MEDIA", direction = Relationship.Direction.OUTGOING)
    var medias: Set<Media>,
    @Relationship(value = "IS_SHOP", direction = Relationship.Direction.OUTGOING)
    var shop: Shop?=null,
    var status: StatusType? = StatusType.ACTIVATE,
    @CreatedBy val createdBy: User
) {
}