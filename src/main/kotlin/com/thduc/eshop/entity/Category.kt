package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.neo4j.ogm.annotation.Index
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship

@NodeEntity
class Category(
    @Id @GeneratedValue var id: Long?,
//    @Index(unique = true)
    @Index(unique = true,)
    var name: String?,
    @Relationship("HAS_IMAGE",direction = Relationship.Direction.OUTGOING) var image: Media?,
    var status: StatusType? = StatusType.ACTIVATE
) {


}