package com.thduc.eshop

import com.thduc.eshop.entity.Role
import com.thduc.eshop.entity.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder
import java.util.HashSet

import com.thduc.eshop.repository.RoleRepository

import com.thduc.eshop.repository.UserRepository

import org.springframework.boot.CommandLineRunner
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
class EshopApplication{

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun initData(userRepository: UserRepository, roleRespository: RoleRepository?): CommandLineRunner? {
        return CommandLineRunner { _: Array<String?>? ->
            if (userRepository.count() == 0L) {
                var user:User = User(username = "admin",password = encoder()!!.encode("congchuabuoito"),fullname = "Admin",)
                user.roles = setOf(Role(roleName = "ADMIN"),Role(roleName = "USER"),Role(roleName = "MERCHANT"))
                userRepository.save(user)
            }
        }
    }
}
//@Bean
//fun encoder(): PasswordEncoder? {
//    return BCryptPasswordEncoder()
//}
fun main(args: Array<String>) {
    runApplication<EshopApplication>(*args)
}
