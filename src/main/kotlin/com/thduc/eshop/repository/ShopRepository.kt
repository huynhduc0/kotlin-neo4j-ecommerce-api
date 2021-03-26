package com.thduc.eshop.repository

import com.thduc.eshop.entity.Shop
import com.thduc.eshop.entity.User
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository:Neo4jRepository<Shop,Long> {
    fun findTopByCreatedBy(user:User):Shop
}