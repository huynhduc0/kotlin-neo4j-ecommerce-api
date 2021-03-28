package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.TargetNode

@NodeEntity
class OrderDetail(
    @Id @GeneratedValue var id: Long? = null,
    @Relationship(value = "ORDERED_PRODUCT",direction = Relationship.Direction.OUTGOING)
    val product: Product,
    val unitPrice: Long?,
    val quantity: Int?,
    val status: StatusType? = StatusType.NEW
) {

}