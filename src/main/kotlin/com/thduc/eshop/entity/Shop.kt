package com.thduc.eshop.entity

import com.thduc.eshop.constant.StatusType
import org.springframework.data.annotation.CreatedBy
import javax.persistence.*

@Entity
class Shop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var name:String? = null,
    @OneToOne(cascade = [CascadeType.PERSIST],fetch = FetchType.EAGER)
    var mainAddress: Address? = null,
    @OneToOne
    var user: User? = null,
    var status: StatusType?= StatusType.ACTIVATE,
    @OneToOne
    var card: Card? = null

) {
    constructor(): this(null,null,null,null,null,null)

}