package com.thduc.eshop.repository

import com.thduc.eshop.entity.Card
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository: PagingAndSortingRepository<Card, Long> {
}