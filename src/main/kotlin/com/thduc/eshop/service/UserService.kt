package com.thduc.eshop.service

import com.thduc.eshop.config.SecurityProperty
import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.DataNotFoundException
import com.thduc.eshop.repository.UserRepository
import com.thduc.eshop.request.UserForm
import com.thduc.eshop.request.UserResponse
import com.thduc.eshop.service.ServiceImpl.UserServiceImpl
import com.thduc.eshop.utilities.FileUtil
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired val passwordEncoder: PasswordEncoder,
    @Autowired val appUserDetailsService: AppUserDetailsService,
    @Autowired val securityProperty: SecurityProperty,
    val fileUtil: FileUtil,
    val roleService: RoleService
) : UserServiceImpl {
    @Transactional
    @PostMapping("register")
    fun createUser(userForm: UserForm): User {
        val user = User(
            username = userForm.username,
            fullname = userForm.fullname,
            phoneNumber = userForm.phoneNumber
        )
//        user = userRepository.save(user)
        user.roles = if (userForm.isShop) setOf(roleService.findRoleByRoleName("MERCHANT")) else
            setOf(roleService.findRoleByRoleName("USER"))
        user.avatar = fileUtil.store(user.username!!, user, userForm.avatar, UploadType.AVATAR).mediaPath
        return userRepository.save(user)
    }
    fun findByUsername(username:String):User{
        return userRepository.findByUsername(username)
    }
    fun checkLogin(u: String, pass: String?): User? {
        val user: User = userRepository.findByUsername(u)
        return if (passwordEncoder.matches(pass, user.password)) user else null
    }

    @Throws(DataNotFoundException::class)
    fun login(userForm: UserForm): UserResponse {
        val user: User = userRepository.findByUsername(username = userForm.username)
////        if (user!=null) return UserResponse("hi",user)
//        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
//        user.roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }
//        val authorString: String? = authorities.stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(","));
////        val authorities: String = appUserDetailsService.loadUserByUsername(userForm.username).authorities.stream()
////            .map(GrantedAuthority::getAuthority)
////            .collect(Collectors.joining(","))
        val userDetails: UserDetails = appUserDetailsService.loadUserByUsername(userForm.username)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, securityProperty.expirationTime)
        val token = Jwts.builder()
            .setSubject(userForm.username)
            .claim("auth", userDetails.authorities)
            .setExpiration(calendar.time)
            .signWith(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
        return if (token.isNullOrEmpty()) throw DataNotFoundException("user", "user", "username") else UserResponse(token, user)
    }
}