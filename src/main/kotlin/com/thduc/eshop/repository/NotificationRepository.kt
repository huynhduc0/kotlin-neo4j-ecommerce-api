package com.thduc.eshop.repository

import com.thduc.eshop.entity.Notification
import org.springframework.data.repository.PagingAndSortingRepository


interface NotificationRepository: PagingAndSortingRepository<Notification, Long> {
}