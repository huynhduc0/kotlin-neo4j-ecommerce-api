package com.thduc.eshop.service

import com.thduc.eshop.entity.Card
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.CardRepository
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.ServiceImpl.CardServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CardService(
    @Autowired val cardRepository: CardRepository
) : CardServiceImpl {
    override fun getMyCard(userPrincipal: UserPrincipal): Set<Card> {
        return cardRepository.findAllByUser(userPrincipal.currentUser!!)
    }

    override fun update(id: Long, card: Card, currentUser: User?): Card {
        val old = cardRepository.findById(id).orElseThrow { DataNotFoundException("card", "id", id.toString()) }
        card.id = old.id
        return cardRepository.save(card)
    }

    override fun delete(id: Long, currentUser: User?): Boolean {
        val old = cardRepository.findById(id).orElseThrow { DataNotFoundException("address", "id", id.toString()) }
        cardRepository.delete(old)
        return true

    }

    override fun add(card: Card, currentUser: User?): Card {
        card.user = currentUser
        return cardRepository.save(card)

    }

}