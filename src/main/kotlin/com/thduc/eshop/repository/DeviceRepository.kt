package com.thduc.eshop.repository

import com.thduc.eshop.entity.Device
import org.springframework.data.neo4j.repository.Neo4jRepository

interface DeviceRepository:Neo4jRepository<Device,Long> {
}