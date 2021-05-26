package com.thduc.eshop.service


import com.thduc.eshop.constant.OSType
import com.thduc.eshop.entity.Device
import com.thduc.eshop.entity.User
import com.thduc.eshop.repository.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeviceService(
    @Autowired
    val deviceRepository: DeviceRepository
) {
    fun refreshToken(pushToken: String?, osType: OSType?, deviceId: String?,user: User): Device? {
        if(pushToken == null || deviceId == null || osType == null) return null
        if(deviceRepository.existsByDeviceIdAndPushTokenAndUser(deviceId!!,pushToken!!,user)) return null
        val oldToken: Device? = deviceRepository.findTopByDeviceId(deviceId!!)
        return if (oldToken != null) {
            oldToken.pushToken = pushToken
            oldToken.user = user
            deviceRepository.save<Device>(oldToken)
            null
        } else {
            deviceRepository.save(
                Device(pushToken = pushToken, os = osType, deviceId = deviceId,user = user))

        }
    }

    fun deleteAllToken(deviceId: String) {
        deviceRepository.deleteAllByDeviceId(deviceId)
    }
    fun getAllDevice(user:User):Set<Device>?{
        return deviceRepository.findAllByUser(user)
    }
}