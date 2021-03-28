package com.thduc.eshop.entity

import com.thduc.eshop.constant.OSType
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id

@NodeEntity
class Device(
    @Id @GeneratedValue
    var id: Long? = null,
    var deviceId: String,
    var jwtToken:String? =null,
    var push_token:String? = null,
    var os: OSType,
    @CreatedBy var user: User? = null,
) {

}