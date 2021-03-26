package com.thduc.eshop.entity

import com.thduc.eshop.relationship.Order
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@Node
class User(
    @Id @GeneratedValue var id:Long?  = null,
    @UniqueElements
    var username: String,
    var fullname: String,
    var password: String? = null,
    @UniqueElements
    var socialId: String? = null,
    var phoneNumber: String? = null,
    var avatar: Media? = null,
    @Relationship(type = "HAS_ADDRESS", direction = Relationship.Direction.OUTGOING) var addresses: Set<Address>? = null,
    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING) var roles: Set<Role>? = null,
    @CreatedDate var created: Date? = Date(),
    @LastModifiedDate var updated: Date = Date(),
) {

}