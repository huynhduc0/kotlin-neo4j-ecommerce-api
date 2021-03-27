package com.thduc.eshop.service

import com.thduc.eshop.entity.Role
import com.thduc.eshop.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService(
    @Autowired val roleRepository: RoleRepository
):RoleServiceImpl {
    fun findRoleByRoleName(name: String): Role{
        return roleRepository.findFirstByRoleName(name)
    }
}