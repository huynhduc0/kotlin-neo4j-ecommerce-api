package com.thduc.eshop.repository

import com.thduc.eshop.entity.Role
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository:Neo4jRepository<Role,Long> {
    fun findFirstByRoleName(roleName:String):Role
}