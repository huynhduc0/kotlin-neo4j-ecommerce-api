package com.thduc.eshop.entity

import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship

@NodeEntity
class Rating(
    @Id @GeneratedValue
    var id: Long?=null,
    var star: Int,
    @Relationship(value = "FROM_PRODUCT",direction = Relationship.Direction.INCOMING)
    var product: Product,
    @CreatedBy var user: User,
) {
}