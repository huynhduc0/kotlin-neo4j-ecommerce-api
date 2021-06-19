package com.thduc.eshop.service

import com.thduc.eshop.constant.StatusType
import com.thduc.eshop.entity.Banner
import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.BannerRepository
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.service.ServiceImpl.BannerAddServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BannerService(
    @Autowired val bannerRepository: BannerRepository
):BannerAddServiceImpl{
    fun getAllBanner():Set<Banner>{
        return bannerRepository.findAll().toSet()
    }
    fun getAllActivateBanner():Set<Banner>{
        return bannerRepository.findAllByStatus(StatusType.ACTIVATE)
    }
    fun saveBanner( banner: Banner): Banner {
        return bannerRepository.save(banner)
    }

    fun deleteShop(id:Long): SuccessActionResponse {
        val banner = bannerRepository.findById(id).orElseThrow { DataNotFoundException("banner","id", id.toString()) }
        bannerRepository.delete(banner)
        return SuccessActionResponse("delete",true)
    }


}