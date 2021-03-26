package com.thduc.eshop.repository

import com.thduc.eshop.entity.Category
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository
@Repository
interface CategoryRepository:Neo4jRepository<Category,Long> {
}