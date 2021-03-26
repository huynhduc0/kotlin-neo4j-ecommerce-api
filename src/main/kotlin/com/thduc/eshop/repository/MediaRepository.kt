package com.thduc.eshop.repository

import com.thduc.eshop.entity.Media
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaRepository: Neo4jRepository<Media,Long> {
}