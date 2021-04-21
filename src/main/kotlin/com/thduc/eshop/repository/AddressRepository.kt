package com.thduc.eshop.repository

import com.thduc.eshop.entity.Address
import com.thduc.eshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository:PagingAndSortingRepository<Address,Long> {
    fun findAllByUser(user:User, pageable: Pageable):Page<Address>
}