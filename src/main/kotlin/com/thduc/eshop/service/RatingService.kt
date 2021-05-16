package com.thduc.eshop.service

import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.Rating
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.ProductRepository
import com.thduc.eshop.repository.RatingRepository
import com.thduc.eshop.request.RatingForm
import com.thduc.eshop.service.ServiceImpl.RatingServiceImpl
import com.thduc.eshop.utilities.FileUtil

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class RatingService(
    @Autowired val ratingRepository: RatingRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val fileUtil: FileUtil
) : RatingServiceImpl {
    override fun addRating(user: User?, ratingForm: RatingForm): Boolean {
        var rateMedias: Set<Media> = HashSet<Media>()
        var product = productRepository.findById(ratingForm.productId)
            .orElseThrow { DataNotFoundException("product", "id", ratingForm.productId.toString()) }
        ratingForm.medias!!.forEach {
            rateMedias = rateMedias.plus(fileUtil.store(user!!.username!!, user, it, UploadType.RATING))
        }


        val rating = Rating(product = product, rating = ratingForm.rating, medias = rateMedias, user = user,content = ratingForm.content)
        product.rating = (product.rating * product.totalRating!! + ratingForm.rating) / (product.totalRating!! + 1)
        product.totalRating = product.totalRating!! + 1
        ratingRepository.save(rating)
        productRepository.save(product)

        return true
    }

    override fun getProdRating(currentUser: User, of: PageRequest, productId: Long): Page<Rating> {
        val product = productRepository.findById(productId)
            .orElseThrow { DataNotFoundException("product", "id", productId.toString()) }
        return ratingRepository.findAllByProduct(product, of)

    }

    override fun getAllShopRating(currentUser: User, of: PageRequest): Page<Rating> {
        return ratingRepository.findAllByProduct_User(currentUser,of)
    }

}