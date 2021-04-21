package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Address
import com.thduc.eshop.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface AddressSeviceImpl {
    abstract fun addAddress(address: Address, currentUser: User?):Address
    abstract fun getAllByUser(currentUser: User?, of: PageRequest): Page<Address>
    abstract fun update(id: Long, address: Address, currentUser: User?): Address
    abstract fun delete(id: Long, currentUser: User?): Boolean
}