package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Card
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.UserPrincipal

interface CardServiceImpl {
    abstract fun getMyCard(userPrincipal: UserPrincipal): Set<Card>
    abstract fun update(id: Long, card: Card, currentUser: User?): Card
    abstract fun delete(id: Long, currentUser: User?): Boolean
    abstract fun add(card: Card, currentUser: User?): Card
}