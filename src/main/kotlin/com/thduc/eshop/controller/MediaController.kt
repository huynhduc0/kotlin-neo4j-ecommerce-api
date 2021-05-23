package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Media
import com.thduc.eshop.request.MediaForm
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.MediaService
import com.thduc.eshop.utilities.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.io.File
import java.io.IOException


@RestController
@RequestMapping("media")
class MediaController(
    @Autowired val fileUtil: FileUtil,
    @Autowired val mediaService: MediaService
) {
    @GetMapping("{folder}/{filename}")
    @Throws(IOException::class)
    fun loadFile(
        @PathVariable("folder") folder: String?,
        @PathVariable("filename") filename: String?
    ): ResponseEntity<*>? {
        return fileUtil.loadImage(folder, filename!!)
    }

    @GetMapping
    fun getMyMedia(
        @ActiveUser userPrincipal: UserPrincipal,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
        @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ): Page<Media> {
        return if (sortOrder == "desc")
            mediaService.getByUser(
                userPrincipal.currentUser!!,
                PageRequest.of(page, size, Sort.by(sortBy).descending())
            )
        else mediaService.getByUser(userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @PostMapping
    fun addMedia(
        @ActiveUser userPrincipal: UserPrincipal,
        mediaForm: MediaForm
    ):SuccessActionResponse{
        return SuccessActionResponse("add media",mediaService.addMedia(mediaForm,userPrincipal.currentUser!! ))
    }
}