package com.thduc.eshop.service.ServiceImpl

import com.thduc.eshop.entity.Rating
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.RatingForm
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface RatingServiceImpl {
    abstract fun addRating(currentUser: User?, ratingForm: RatingForm): Boolean
    abstract fun getProdRating(currentUser: User, of: PageRequest,productId:Long): Page<Rating>
    abstract fun getAllShopRating(currentUser: User, of: PageRequest): Page<Rating>


}