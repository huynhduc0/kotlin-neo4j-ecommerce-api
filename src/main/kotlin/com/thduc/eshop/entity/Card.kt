package com.thduc.eshop.entity

import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import java.util.*

@NodeEntity
class Card(
    @Id @GeneratedValue var id:Long? =null,
    var cardHolder: String? = null,
    var cardNumber: String? = null,
    var validThrough: Date? = null,
    @CreatedBy var user: User?= null,
) {
}