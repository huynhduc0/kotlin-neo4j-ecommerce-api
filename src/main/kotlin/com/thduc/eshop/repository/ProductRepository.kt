package com.thduc.eshop.repository

import com.thduc.eshop.entity.Product
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository:Neo4jRepository<Product,Long> {
}