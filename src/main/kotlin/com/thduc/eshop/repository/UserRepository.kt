package com.thduc.eshop.repository

import com.thduc.eshop.entity.User
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:Neo4jRepository<User,Long>{
    fun findByUsername(username:String): User
}