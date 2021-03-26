package com.thduc.eshop.service

import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import com.thduc.eshop.repository.ShopRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShopService(
    @Autowired val shopRepository: ShopRepository
):ShopServiceImpl {
    fun findShopByUser(user: User?):Shop{

        return if (shopRepository.findTopByCreatedBy(user!!) == null) Shop(1,null,user)
        else shopRepository.findTopByCreatedBy(user!!)
    }
}