package com.thduc.eshop.repository

import com.thduc.eshop.entity.Role
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: PagingAndSortingRepository<Role, Long> {
    fun findFirstByRoleName(roleName:String):Role
}