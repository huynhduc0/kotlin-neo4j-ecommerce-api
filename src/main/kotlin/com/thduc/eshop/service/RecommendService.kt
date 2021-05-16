package com.thduc.eshop.service

import com.thduc.eshop.entity.Recommend
import com.thduc.eshop.repository.RecommendRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecommendService(
    @Autowired val recommendRepository: RecommendRepository
) {
    fun getAllLikeUid(): List<RecommendRepository.LikeUid?>? {
        return recommendRepository.getAllLikeAno()
    }

    fun getPosts(): List<RecommendRepository.PostIDOnly?>? {
        return recommendRepository.getPostIDOnly()
    }

    fun saveRecommend(recommend: Recommend): Boolean {
        if (!recommendRepository.existsByUserIdAndProductId(
                userId = recommend.userId!!,
                productId = recommend.productId!!
            )
        ) recommendRepository.save(recommend)
        return true
    }

    fun getAllReId(userId: Long): List<Long?>? {
        return recommendRepository.getAllIds(userId)
    }
}