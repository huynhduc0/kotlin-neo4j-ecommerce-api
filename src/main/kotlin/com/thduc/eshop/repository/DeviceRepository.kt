package com.thduc.eshop.repository


import com.thduc.eshop.entity.Device
import com.thduc.eshop.entity.User
import org.springframework.data.repository.PagingAndSortingRepository


interface DeviceRepository: PagingAndSortingRepository<Device, Long> {
    fun findTopByDeviceId(deviceId:String):Device?
    fun deleteAllByDeviceId(deviceId: String)
    fun findAllByUser(user: User):Set<Device>?
    fun existsByDeviceIdAndPushToken(deviceId: String,pushToken: String):Boolean
    fun existsByDeviceIdAndPushTokenAndUser(deviceId: String,pushToken: String,user: User):Boolean
}