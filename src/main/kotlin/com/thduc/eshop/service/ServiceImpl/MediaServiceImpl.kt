package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.MediaForm
import org.springframework.stereotype.Service

@Service
interface MediaServiceImpl {
    abstract fun addMedia(mediaForm: MediaForm, currentUser: User): Boolean
}