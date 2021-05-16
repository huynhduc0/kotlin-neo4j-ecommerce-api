package com.thduc.eshop.request


import com.thduc.eshop.entity.Product
import org.springframework.web.multipart.MultipartFile

class RatingForm (
    val rating:Int,
    val productId: Long,
    val content:String,
    val medias: List<MultipartFile>?
        ){
}