package com.thduc.eshop.controller


import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.entity.Address
import com.thduc.eshop.entity.Card
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CardController(
    @Autowired val cardService: CardService
) {
    @GetMapping
    fun getMyCard(@ActiveUser userPrincipal: UserPrincipal):Set<Card> {
        return cardService.getMyCard(userPrincipal)
    }
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody card: Card, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal): Card {
        return cardService.update(id,card,userPrincipal.currentUser);
    }
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal): SuccessActionResponse {
        return SuccessActionResponse("delete",cardService.delete(id,userPrincipal.currentUser))
    }
    @PostMapping
    fun createAddress(@RequestBody card: Card, @ActiveUser userPrincipal: com.thduc.eshop.request.UserPrincipal):Card{
        return cardService.add(card,userPrincipal.currentUser);
    }
}