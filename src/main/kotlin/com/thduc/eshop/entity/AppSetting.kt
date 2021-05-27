package com.thduc.eshop.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AppSetting(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id:Long? = null,
    var configKey:String? = null,
    var configValue:String? = null,
) {

}