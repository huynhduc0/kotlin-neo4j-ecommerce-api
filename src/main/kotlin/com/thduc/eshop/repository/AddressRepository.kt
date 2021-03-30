package com.thduc.eshop.repository

import com.thduc.eshop.entity.Address

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository:PagingAndSortingRepository<Address,Long> {
}