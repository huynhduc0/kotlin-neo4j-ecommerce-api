package com.thduc.eshop.repository

import com.thduc.eshop.entity.Address
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository:Neo4jRepository<Address,Long> {
}