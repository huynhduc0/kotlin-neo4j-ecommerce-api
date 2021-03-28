package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id

@NodeEntity
class Address (
    @Id @GeneratedValue var id:Long?,
    var address: String?,

    var lat: Float? = null,
    var lon: Float? = null,
    var status: StatusType? = StatusType.ACTIVATE,
    var country:String? = null,
    var postcode:String? = null,
    var street:String?=null,
    var city:String? = null,
    var firstName: String?=null,
    var lastName:String? =null,
    @CreatedBy var user: User
){
}