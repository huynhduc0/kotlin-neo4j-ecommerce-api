package com.thduc.eshop.request

import com.thduc.eshop.entity.Media
import org.springframework.web.multipart.MultipartFile

class UserForm(
    var username: String,
    var fullname: String,
    var password: String? = null,
    var socialId: String? = null,
    var phoneNumber: String? = null,
    var avatar: MultipartFile?,
    var isShop: Boolean,
    var oAuthToken: String? = null,
    var platform:String? = null,
) {
}