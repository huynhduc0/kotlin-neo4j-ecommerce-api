package com.thduc.eshop.entity

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Address (
    @Id @GeneratedValue var id:Long?,
    var address: String?,
    var lat: Float? = null,
    var lon: Float? = null

){
}