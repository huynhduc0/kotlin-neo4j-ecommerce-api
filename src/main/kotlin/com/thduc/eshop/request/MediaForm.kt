package com.thduc.eshop.request

import org.springframework.web.multipart.MultipartFile

class MediaForm(
    var medias: Set<MultipartFile>,
) {
}