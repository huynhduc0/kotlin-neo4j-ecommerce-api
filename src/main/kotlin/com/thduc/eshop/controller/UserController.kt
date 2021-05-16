package com.thduc.eshop.controller

import com.thduc.eshop.annotation.LogExecution
import com.thduc.eshop.entity.User
import com.thduc.eshop.request.UserForm
import com.thduc.eshop.request.UserResponse
import com.thduc.eshop.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController(@Autowired val userService: UserService) {
    @PostMapping("register")
    @Transactional
    fun register(userForm: UserForm):User{
        return userService.createUser(userForm)
    }

    @PostMapping("login")
    @Transactional

    fun login(@RequestBody userForm: UserForm):UserResponse{
        return userService.login(userForm)
    }
    @PostMapping("google")
    @Transactional
    fun googleLogin(userForm: UserForm):UserResponse?{
        return userService.googleLogin(userForm)
    }
}