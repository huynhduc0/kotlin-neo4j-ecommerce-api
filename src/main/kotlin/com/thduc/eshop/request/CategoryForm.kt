package com.thduc.eshop.request

import com.thduc.eshop.constant.StatusType
import org.springframework.web.multipart.MultipartFile

class CategoryForm(
    var name: String,
    var status: StatusType? = StatusType.ACTIVATE,
    var image: MultipartFile?,
) {

}