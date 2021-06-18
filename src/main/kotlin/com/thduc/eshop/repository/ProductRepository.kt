package com.thduc.eshop.repository

import com.thduc.eshop.constant.StatusType
import com.thduc.eshop.entity.Category
import com.thduc.eshop.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


@Repository
interface ProductRepository: PagingAndSortingRepository<Product, Long> {
    fun findAllByStatus(statusType: StatusType,pageable: Pageable): Page<Product>
    fun findAllByCategoriesContains(category: Category,pageable: Pageable):Page<Product>
    fun findAllByCategoriesContainsAndNameContainingOrShop_NameContaining(category: Category,name:String,sName:String,pageable: Pageable):Page<Product>
    fun findAllByNameContainingOrShop_NameContaining(name:String,sName:String,pageable: Pageable):Page<Product>
    fun findAllByStatusAndShop_Id(statusType: StatusType, shopId: Long ,pageable: Pageable): Page<Product>
    fun findAllByCategoriesContainsAndShop_Id(category: Category,shopId: Long,pageable: Pageable):Page<Product>
    fun findAllByCategoriesContainsAndNameContainingAndShop_Id(category: Category,name:String,sName:String,pageable: Pageable):Page<Product>
    fun findAllByNameContainingAndShop_Id(name:String,shopId: Long,pageable: Pageable):Page<Product>

    @Query("SELECT DISTINCT p.* FROM product as p LEFT JOIN (SELECT * FROM recommend where user_id = :id ) as r " +
            "on p.id = r.product_id where p.status= 0 order by r.num desc"
    ,nativeQuery = true,countQuery = "SELECT DISTINCT p.* FROM product as p where p.status= 0  ")
    fun findAllRecommendByStatus(@Param("id") id: Long, pageable: Pageable): Page<Product>

    @Query("SELECT DISTINCT p.* FROM product as p LEFT JOIN (SELECT * FROM recommend where user_id = :id ) as r " +
            "on p.id = r.product_id where p.status= 0 and p.id not in(:exceptIds)  order by r.num desc"
        ,nativeQuery = true,countQuery = "SELECT DISTINCT p.* FROM product as p where p.id not in(:exceptIds) and p.status= 0 ")
    fun findAllByStatusExcept(@Param("id") id: Long, @Param("exceptIds") exceptIds:List<Long>?, pageable: Pageable): Page<Product>

    @Query("SELECT c.id FROM Cart as c where c.user.id = :id")
    fun getCartIds(@Param("id") id: Long):List<Long>

    @Query("SELECT DISTINCT p.* FROM product as p JOIN product_categories on product_categories.product_id = p.id LEFT JOIN (SELECT distinct product_id,num FROM recommend  ) as r " +
            "on p.id = r.product_id where p.status= 0 and p.id not in(:exceptIds) and id <>:pid order by CASE product_categories.categories_id WHEN :categoryId THEN 1 ELSE 2 END  ,r.num desc"
        ,nativeQuery = true,countQuery = "SELECT DISTINCT p.* FROM product as p where p.status = 0 and p.id not in(:exceptIds) and id <>:pid")
    fun findAllByStatusExceptAndCategories(@Param("pid") pid: Long,@Param("categoryId") categoryId: Long, @Param("exceptIds") exceptIds:List<Long>?, pageable: Pageable): Page<Product>

//    override fun customize(
//        bindings: QuerydslBindings, root: EntityPath<Product>
//    ) {
//        bindings.bind(String::class.java)
//            .first(SingleValueBinding { obj: StringPath, str: String? ->
//                obj.containsIgnoreCase(
//                    str
//                )
//            })
//        bindings.excluding(root)
//    }
}