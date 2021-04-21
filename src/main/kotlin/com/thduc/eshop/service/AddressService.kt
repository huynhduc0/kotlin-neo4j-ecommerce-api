package com.thduc.eshop.service

import com.thduc.eshop.entity.Address
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.AddressRepository
import com.thduc.eshop.service.ServiceImpl.AddressSeviceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class AddressService(@Autowired val addressRepository: AddressRepository):AddressSeviceImpl {
    override fun addAddress(address: Address, currentUser: User?): Address {
        address.user = currentUser
        return addressRepository.save(address)
    }

    override fun getAllByUser(currentUser: User?, of: PageRequest): Page<Address> {
        return addressRepository.findAllByUser(currentUser!!,of)
    }

    override fun update(id: Long, address: Address, currentUser: User?): Address {
        var old =  addressRepository.findById(id).orElseThrow{DataNotFoundException("address","id",id.toString())}
        address.id = old.id
        return addressRepository.save(address)
    }

    override fun delete(id: Long, currentUser: User?): Boolean {
        var old =  addressRepository.findById(id).orElseThrow{DataNotFoundException("address","id",id.toString())}
        addressRepository.delete(old)
        return true
    }


}