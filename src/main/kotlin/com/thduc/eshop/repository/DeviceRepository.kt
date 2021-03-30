package com.thduc.eshop.repository

import com.thduc.eshop.entity.Device
import org.springframework.data.repository.PagingAndSortingRepository


interface DeviceRepository: PagingAndSortingRepository<Device, Long> {
}