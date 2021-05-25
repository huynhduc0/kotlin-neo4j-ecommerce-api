package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import com.thduc.eshop.entity.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.sql.Date
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @OneToOne var user: User?,
    @OneToMany(cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var orderDetails: Set<OrderDetail>?,
    var fee: Double? = 0.0,
    var total: Double?= 0.0,
    @OneToOne
    var shippingAddress: Address?,
    @OneToOne
    var shop: Shop? = null,
    var status: StatusType? = StatusType.NEW,
    @CreationTimestamp var created: LocalDateTime? = null,
    @UpdateTimestamp var updated: LocalDateTime? = null
) {
    constructor(): this(null,null,null,null,null,null)
}