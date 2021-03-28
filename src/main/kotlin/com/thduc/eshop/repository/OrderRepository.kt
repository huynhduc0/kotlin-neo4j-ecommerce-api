package com.thduc.eshop.repository

import com.thduc.eshop.relationship.Order
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository:Neo4jRepository<Order,Long> {
}