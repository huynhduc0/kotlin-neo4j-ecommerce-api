package com.thduc.eshop.repository;

import com.thduc.eshop.entity.Banner
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository;

@Repository
interface BannerRepository: PagingAndSortingRepository<Banner,Long> {

}
