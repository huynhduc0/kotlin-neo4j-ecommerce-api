package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import javax.persistence.*

@Entity
data class Address (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long?,
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
    @OneToOne
    var user: User? = null,
//    var userId: Long? = null
){
    constructor(): this(null,null,null)
}
