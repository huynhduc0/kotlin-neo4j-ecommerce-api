package com.thduc.eshop.service

import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.ShopRepository
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.service.ServiceImpl.ShopServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShopService(
    @Autowired val shopRepository: ShopRepository
) : ShopServiceImpl {
    fun findShopByUser(user: User?): Shop {
        return shopRepository.findTopByCreatedBy(user!!)
    }

    fun saveShop(user: User, shop: Shop): Shop {
        val currentShop: Shop = if (shop.id != null)
            shopRepository.findById(shop.id!!).orElse(
                throw DataNotFoundException(
                    "shop", "shop",
                    shop.id.toString()
                )
            ) else shop
        currentShop.createdBy = user
        return shopRepository.save(currentShop)
    }

    fun deleteShop(user: User): SuccessActionResponse {
        val shop: Shop = shopRepository.findTopByCreatedBy(user)
        shopRepository.delete(shop)
        return SuccessActionResponse("delete",true)
    }
}