package com.thduc.eshop.repository

import com.thduc.eshop.entity.Card
import org.springframework.data.neo4j.repository.Neo4jRepository

interface CardRepository:Neo4jRepository<Card,Long> {
}