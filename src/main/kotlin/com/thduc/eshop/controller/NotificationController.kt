package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.AppNotification
import com.thduc.eshop.entity.Media
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@RestController
@RequestMapping("notifications")
class NotificationController(
    @Autowired val notificationService: NotificationService
) {
    @GetMapping
    fun allNoti(@ActiveUser userPrincipal: UserPrincipal,
                @RequestParam(value = "page", defaultValue = "0") page: Int,
                @RequestParam(value = "size", defaultValue = "10") size: Int,
                @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ): Page<AppNotification> {
        return if (sortOrder == "desc")
            notificationService.getByUser(
                userPrincipal.currentUser!!,
                PageRequest.of(page, size, Sort.by(sortBy).descending())
            )
        else notificationService.getByUser(userPrincipal.getUser()!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @GetMapping("{id}")
    fun seen(@PathVariable id:Long){
        return notificationService.seen(id);
    }
}
