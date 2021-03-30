package com.thduc.eshop.entity

import com.thduc.eshop.constant.OSType
import javax.persistence.*

@Entity
data class Device(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var deviceId: String? = null,
    var jwtToken:String? =null,
    var push_token:String? = null,
    var os: OSType = OSType.WEB,
    @OneToOne var user: User? = null,
) {
    constructor(): this(null,null,null)
}