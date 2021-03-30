package com.thduc.eshop.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thduc.eshop.constant.GenderType
import com.thduc.eshop.constant.StatusType
import java.util.*
import javax.persistence.*

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id:Long?  = null,
    var username: String?,
    var fullname: String?,
    @JsonIgnore
    var password: String? = null,
    var socialId: String? = null,
    var phoneNumber: String? = null,
    var gender: GenderType? = GenderType.HIDE,
    var avatar: String? = null,
    @ManyToMany(cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var roles: Set<Role>? = null,
    var status: StatusType? = StatusType.ACTIVATE,
    var dateOfBirth: Date? = null
) {
    constructor(): this(null,null,null,null,null,null,null,null,null,null
    ,null)

}