package com.thduc.eshop.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.thduc.eshop.annotation.LogExecution
import com.thduc.eshop.config.SecurityProperty
import com.thduc.eshop.constant.OSType
import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.User
import com.thduc.eshop.exception.BadRequestException
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    fun createUser(userForm: UserForm): User {
        val user = User(
            username = userForm.username,
            fullname = userForm.fullname,
            phoneNumber = userForm.phoneNumber,
            email = userForm.email
        )
//        user = userRepository.save(user)
        user.roles = if (userForm.isShop!!) setOf(roleService.findRoleByRoleName("MERCHANT")) else
            setOf(roleService.findRoleByRoleName("USER"))
        user.avatar = fileUtil.store(user.username!!, user, userForm.avatar, UploadType.AVATAR).mediaPath
        user.password = if (userForm.password == null) null else passwordEncoder.encode(userForm.password)
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun checkLogin(u: String, pass: String?): User? {
        val user: User? = userRepository.findByUsername(u)
        return if (passwordEncoder.matches(pass, user!!.password)) user else null
    }

    @Throws(DataNotFoundException::class)
    @LogExecution
    fun login(userForm: UserForm): UserResponse {
        val user: User = userRepository.findByUsername(username = userForm.username!!)
            ?: throw BadRequestException("Wrong username or password")
        if (!passwordEncoder.matches(userForm.password, user.password))
            throw BadRequestException("Wrong username or password")
        ////        if (user!=null) return UserResponse("hi",user)
//        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
//        user.roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }
//        val authorString: String? = authorities.stream()
//            .map(GrantedAuthority::getAuthority)
//            .collect(Collectors.joining(","));
////        val authorities: String = appUserDetailsService.loadUserByUsername(userForm.username).authorities.stream()
////            .map(GrantedAuthority::getAuthority)
////            .collect(Collectors.joining(","))
        val token = generateToken(userForm.username!!)
        return if (token.isNullOrEmpty()) throw DataNotFoundException("user", "user", "username") else UserResponse(
            token,
            user
        )
    }

    fun generateToken(username: String): String? {
        val userDetails: UserDetails = appUserDetailsService.loadUserByUsername(username)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, securityProperty.expirationTime)
        return Jwts.builder()
            .setSubject(username)
            .claim("auth", userDetails.authorities)
            .setExpiration(calendar.time)
            .signWith(Keys.hmacShaKeyFor(securityProperty.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun googleLogin(userForm: UserForm): UserResponse? {
        val transport = NetHttpTransport()
        val jsonFactory = GsonFactory()
        val key: String = retrieveKey(userForm.platform!!)
        val verifier = GoogleIdTokenVerifier.Builder(
            transport,
            jsonFactory
        ).setAudience(Collections.singleton(key))
            .build()
        val idToken: GoogleIdToken = GoogleIdToken.parse(verifier.jsonFactory, userForm.oAuthToken)
        val isValid: Boolean = (idToken != null) && verifier.verify(idToken)
        if (isValid) {
            val payload = idToken.payload
            val email = payload.email
            val userId = payload.subject
            var user: User? = userRepository.findTopBySocialId(userId)
            if (user != null) {
                val token = generateToken(user.username!!)
                return if (token.isNullOrEmpty()) throw DataNotFoundException(
                    "user",
                    "user",
                    "username"
                ) else UserResponse(
                    token,
                    user
                )
            } else {
                val emailVerified: Boolean = java.lang.Boolean.valueOf(payload.emailVerified)
                val name = payload["name"]
                val pictureUrl = payload["picture"] as String
                val locale = payload["locale"]
                val familyNameU = payload["family_name"]
                var familyName: String = familyNameU.toString()
                val givenName = payload["given_name"]
                var user = User(
                    username = email.split("@")[0],
                    fullname = "$familyName $givenName",
                    phoneNumber = userForm.phoneNumber,
                    socialId = userId,
                    email = email

                )
                user.roles = if (userForm.isShop!!) setOf(roleService.findRoleByRoleName("MERCHANT")) else
                    setOf(roleService.findRoleByRoleName("USER"))
                user.avatar= pictureUrl
                val newUser: User = userRepository.save(user)
                val token = generateToken(user.username!!)
                return if (token.isNullOrEmpty()) throw DataNotFoundException(
                    "user",
                    "user",
                    "username"
                ) else UserResponse(
                    token,
                    newUser
                )

            }
        } else {
            throw BadRequestException("OAuth token is invalid")
        }
        return null
    }

    fun retrieveKey(platform: OSType): String {

        return when (platform) {
            OSType.WEB -> "213239061541-lhm7o6vso9gsu58lvfv31svmj4bio7tf.apps.googleusercontent.com"
            OSType.IOS -> "213239061541-4aaq1mirj2m41j9rmemvmi4hu9d2bo6m.apps.googleusercontent.com"
            else -> "nothing"
        }
    }

    override fun getAllUser(currentUser: User, of: PageRequest): Page<User> {
        return userRepository.findAllByIdNot(currentUser.id!!,of)
    }

}