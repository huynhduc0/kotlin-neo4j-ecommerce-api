package com.thduc.eshop.repository

import com.thduc.eshop.entity.Recommend
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RecommendRepository:JpaRepository<Recommend,Long> {
    fun existsByUserIdAndProductId(userId:Long,productId:Long):Boolean

    @Query("select p.productId from Recommend p where p.userId = :from_id")
    fun getAllIds(@Param("from_id") from_id: Long?): List<Long?>?

    @Query(
        value = """SELECT distinct cart.user_id as uid, product_id as pid, rating*total_rating as num
        FROM cart JOIN product on product.id = cart.product_id""", nativeQuery = true
    )
    fun getAllLikeAno(): List<LikeUid?>?
    interface LikeUid {
        val uid: Long
        val pid: Long
        val num: Long
    }

    @Query(value = "SELECT id as pid FROM product ", nativeQuery = true)
    fun getPostIDOnly(): List<PostIDOnly?>?
    interface PostIDOnly {
        val pid: Long
    }
}