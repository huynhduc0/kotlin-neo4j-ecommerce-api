package com.thduc.eshop.controller

import com.thduc.eshop.annotation.ActiveUser
import com.thduc.eshop.annotation.LogExecution
import com.thduc.eshop.entity.Rating
import com.thduc.eshop.request.RatingForm
import com.thduc.eshop.request.SuccessActionResponse
import com.thduc.eshop.request.UserPrincipal
import com.thduc.eshop.service.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("rating")
class RatingController(
    @Autowired val ratingService: RatingService
) {
    @PostMapping
    @Transactional
    fun addRating(@ActiveUser userPrincipal: UserPrincipal, ratingForm:RatingForm): SuccessActionResponse {
        return SuccessActionResponse("Rating",ratingService.addRating(userPrincipal.currentUser,ratingForm))
    }
    @GetMapping("{id}")
    @Transactional
    fun loadRating(@ActiveUser userPrincipal: UserPrincipal,@PathVariable id:Long,
                   @RequestParam(value = "page", defaultValue = "0") page: Int,
                   @RequestParam(value = "size", defaultValue = "10") size: Int,
                   @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                   @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ): Page<Rating> {
        return if (sortOrder == "desc")
            ratingService.getProdRating(
                userPrincipal.currentUser!!,
                PageRequest.of(page, size, Sort.by(sortBy).descending()),
                id
            )
        else ratingService.getProdRating(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy)),id)
    }
    @GetMapping("shop")
    @Transactional
    @PreAuthorize("hasRole('MERCHANT')")
    @LogExecution
    fun loadMyShopRating(@ActiveUser userPrincipal: UserPrincipal,
                   @RequestParam(value = "page", defaultValue = "0") page: Int,
                   @RequestParam(value = "size", defaultValue = "10") size: Int,
                   @RequestParam(value = "sortBy", defaultValue = "id") sortBy: String,
                   @RequestParam(value = "sortOrder", defaultValue = "") sortOrder: String
    ): Page<Rating> {
        return if (sortOrder == "desc")
            ratingService.getAllShopRating(
                userPrincipal.currentUser!!,
                PageRequest.of(page, size, Sort.by(sortBy).descending()),
            )
        else ratingService.getAllShopRating(userPrincipal.currentUser!!, PageRequest.of(page, size, Sort.by(sortBy)))
    }
    @GetMapping("execute")
    @LogExecution
    fun loadAllRating(): List<Rating>{
        return ratingService.loadAll()
    }
    @GetMapping("setSys/{id}/{point}")
    @LogExecution
    fun set(@PathVariable id:Long, @PathVariable point:Int):Boolean{
        return ratingService.changeSys(id,point)
    }
    @GetMapping("summary")
    @LogExecution
    fun summary():Boolean{
        return ratingService.productSummary()
    }
}