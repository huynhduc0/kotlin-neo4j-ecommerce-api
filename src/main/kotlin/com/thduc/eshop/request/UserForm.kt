package com.thduc.eshop.request

import com.thduc.eshop.constant.OSType
import com.thduc.eshop.entity.Media
import org.springframework.web.multipart.MultipartFile

class UserForm(
    var username: String? = null,
    var fullname: String? = null,
    var password: String? = null,
    var socialId: String? = null,
    var phoneNumber: String? = null,
    var avatar: MultipartFile?,
    var isShop: Boolean? = null,
    var oAuthToken: String? = null,
    var platform:OSType? = null,
    var email:String? = null,
    var deviceId:String? = null,
    var pushToken:String? = null,
) {
}