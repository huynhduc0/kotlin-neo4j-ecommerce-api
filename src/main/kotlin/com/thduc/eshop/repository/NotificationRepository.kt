package com.thduc.eshop.repository

import com.thduc.eshop.entity.Notification
import org.springframework.data.neo4j.repository.Neo4jRepository

interface NotificationRepository: Neo4jRepository<Notification,Long>{
}