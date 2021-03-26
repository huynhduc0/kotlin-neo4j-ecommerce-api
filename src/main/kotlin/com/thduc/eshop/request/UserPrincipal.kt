package com.thduc.eshop.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.thduc.eshop.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.ArrayList
import java.util.stream.Collectors


class UserPrincipal : UserDetails {
    var id: Long = 0
    var name: String? = null
    private var username: String? = null

    @JsonIgnore
    private var password: String? = null
    private var user: User? = null
    var grantedAuthorities: List<GrantedAuthority> = ArrayList()

    constructor() {}
    constructor(
        id: Long,
        name: String?,
        username: String?,
        password: String?,
        user: User?,
        grantedAuthorities: List<GrantedAuthority>
    ) {
        this.id = id
        this.name = name
        this.username = username
        this.password = password
        this.user = user
        this.grantedAuthorities = grantedAuthorities
    }

    fun getUser(): User? {
        return user
    }

    fun setUser(user: User?) {
        this.user = user
    }

    override fun getUsername(): String {
        return username!!
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val user = o as UserPrincipal
        return id == user.id
    }

    companion object {
        fun build(user: User): UserPrincipal {
//            val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role ->
//                SimpleGrantedAuthority(
//                    role.roleName.toString()
//                )
//            }.collect(Collectors.toList())
            val authorities = ArrayList<SimpleGrantedAuthority>()
                user.roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }
            return UserPrincipal(
                user.id!!,
                user.username,
                user.username,
                user.password,
                user,
                authorities
            )
        }
    }
}