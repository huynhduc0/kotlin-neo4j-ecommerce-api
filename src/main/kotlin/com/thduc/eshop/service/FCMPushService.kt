package com.thduc.eshop.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.stereotype.Service
import org.springframework.core.io.ClassPathResource

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification
import com.thduc.eshop.constant.UploadConstant
import com.thduc.eshop.constant.UploadConstant.SERVER_PATH
import com.thduc.eshop.entity.AppNotification
import com.thduc.eshop.repository.DeviceRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


import javax.annotation.PostConstruct
import java.io.IOException


@Service
class FCMPushService(
    @Autowired val deviceRepository: DeviceRepository

) {
    //    @Value("${app.firebase-config}")
    val firebaseConfig:String = "techshop-e780f-firebase-adminsdk-1hquf-3f491e4e4a.json"
    var firebaseApp: FirebaseApp? = null
    var log: org.slf4j.Logger? = LoggerFactory.getLogger(FCMPushService::class.java)
    @PostConstruct
    private fun initialize() {
        try {
            val options: FirebaseOptions = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(ClassPathResource(firebaseConfig).inputStream)).build()
            firebaseApp = if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            } else {
                FirebaseApp.getInstance()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
    //        try {
    //            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
    //                    subscriptionRequestDto.getTopicName());
    //        } catch (FirebaseMessagingException e) {
    //            log.error("Firebase subscribe to topic fail", e);
    //        }
    //    }
    //
    //    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
    //        try {
    //            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
    //                    subscriptionRequestDto.getTopicName());
    //        } catch (FirebaseMessagingException e) {
    //            log.error("Firebase unsubscribe from topic fail", e);
    //        }
    //    }
    fun sendPnsToDevice(notifications: AppNotification): Boolean {
        val devices = deviceRepository.findAllByUser(notifications.toUser!!)
        devices!!.forEach { tokens ->
            val message: Message? = Message.builder()
                .setToken(tokens.pushToken)
                .setNotification(
                    Notification.builder().
                    setImage(SERVER_PATH+notifications.image).setBody(notifications.message).setTitle(notifications.title)
                        .build()
                )
                .putData("notification_type",notifications.notificationType.toString())
                .putData("destinationId", notifications.destinationId.toString())
                .build()
            var response: String? = null
            try {
                response = FirebaseMessaging.getInstance().send(message)
                log!!.info("done push",response)
            } catch (e: FirebaseMessagingException) {
                e.printStackTrace()
                log!!.error("Fail to send firebase notification", e)
            }
        }
        return true
    }

}