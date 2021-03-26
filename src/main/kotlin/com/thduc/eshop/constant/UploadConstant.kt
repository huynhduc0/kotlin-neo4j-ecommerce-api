package com.thduc.eshop.constant

import org.springframework.stereotype.Component


@Component
object UploadConstant {
    val UPLOAD_PATH = System.getProperty("user.dir") + "/src/main/resources/static/uploads/"
}