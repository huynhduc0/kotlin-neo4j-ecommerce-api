package com.thduc.eshop.repository;

import com.thduc.eshop.constant.StatusType
import com.thduc.eshop.entity.Banner
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository;

@Repository
interface BannerRepository: PagingAndSortingRepository<Banner,Long> {
    fun findAllByStatus(status: StatusType):Set<Banner>
}
