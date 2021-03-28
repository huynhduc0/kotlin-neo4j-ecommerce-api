package com.thduc.eshop.entity

import com.thduc.eshop.constant.GenderType
import com.thduc.eshop.constant.StatusType
import org.hibernate.validator.constraints.UniqueElements
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import java.util.*

@NodeEntity
class User(
    @Id @GeneratedValue var id:Long?  = null,
    @UniqueElements
    var username: String,
    var fullname: String,
    var password: String? = null,
    @UniqueElements
    var socialId: String? = null,
    var phoneNumber: String? = null,
    var gender: GenderType? = GenderType.HIDE,
    var avatar: Media? = null,
    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING) var roles: Set<Role>? = null,
    @CreatedDate var created: Date? = Date(),
    @LastModifiedDate var updated: Date = Date(),
    var status: StatusType? = StatusType.ACTIVATE,
    var dateOfBirth: Date? = null
) {

}