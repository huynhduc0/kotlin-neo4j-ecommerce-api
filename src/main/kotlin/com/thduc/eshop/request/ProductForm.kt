package com.thduc.eshop.request

import com.thduc.eshop.constant.StatusType
import com.thduc.eshop.entity.Category
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.ProductProperty
import org.springframework.web.multipart.MultipartFile

class ProductForm(
    var name:String?,
    var shortDescription: String?,
    var description: String,
    var categories: Set<Category>,
    var stock: Int?,
    var medias: Set<Media>,
    var status: StatusType? = StatusType.ACTIVATE,
    var properties: Set<ProductProperty>?
) {
}