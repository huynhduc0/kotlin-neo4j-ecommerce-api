package com.thduc.eshop.relationship

import com.thduc.eshop.entity.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.neo4j.core.schema.*
import java.util.*

@RelationshipProperties
class Order(
    @Id
    @GeneratedValue
    var id: Long?,
    @CreatedBy var user: User,
    @CreatedDate
    var created: Date = Date(),
    @LastModifiedDate
    var updated: Date = Date(),
    @Relationship(value = "ORDER_DETAIL",direction = Relationship.Direction.OUTGOING)
    var orderDetails: Set<OrderDetail>,
    var fee: Double? = 0.0,
    var total: Double?= 0.0,
    @Relationship(value = "SHIPPING_ADDRESS",direction = Relationship.Direction.OUTGOING)
    var shippingAddress: Address,
    @Relationship(value = "FROM_SHOP",direction = Relationship.Direction.OUTGOING)
    var shop: Shop? = null
) {

}