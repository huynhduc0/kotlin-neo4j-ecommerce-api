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
        value = "SELECT DISTINCT\n" +
                "\tcart.user_id AS uid,\n" +
                "\tcart.product_id AS pid,\n" +
                "\tIFNULL(rating * total_rating+ total_rating + IFNULL(t.times,0),0) AS num \n" +
                "FROM\n" +
                "\tcart\n" +
                "\tLEFT JOIN ( SELECT count(*) AS times,product_id FROM order_detail GROUP BY order_detail.product_id ) AS t on cart.product_id = t.product_id\n" +
                "\tJOIN product ON product.id = cart.product_id", nativeQuery = true
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