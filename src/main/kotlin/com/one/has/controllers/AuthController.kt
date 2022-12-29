package com.one.has.controllers

import com.one.has.dtos.LoginDTO
import com.one.has.dtos.UserDTO
import com.one.has.services.JwtService
import com.one.has.services.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@RequestMapping("api/auth")
class AuthController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val jwtService: JwtService
){
    @PostMapping("create-admin")
    fun createAdmin(
        @RequestBody
        adminRegistrationDetails: UserDTO
    ): ResponseEntity<UserDTO> {
        adminRegistrationDetails.roleId = 0

        return ResponseEntity.ok(userService.saveUser(adminRegistrationDetails))
    }

    @PostMapping("register")
    fun register(
        @RequestBody
        userRegistrationDetails: UserDTO
    ): ResponseEntity<UserDTO> {
        userRegistrationDetails.roleId = 2

        return ResponseEntity.ok(userService.saveUser(userRegistrationDetails))
    }

    @PostMapping("login")
    fun login(
        @RequestBody
        userLoginDetails: LoginDTO,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        try {
            val userExists = userService.determineIfUserIsValid(userLoginDetails.email, userLoginDetails.password)

            if (!userExists) {
                return ResponseEntity.badRequest().body("Invalid credentials")
            }

            val user = userService.findUserByEmail(userLoginDetails.email)

            val token = jwtService.generateJWTTokenForUser(user.id)

            val cookie = Cookie("jwt", token)
            cookie.isHttpOnly = true

            response.addCookie(cookie)

            return ResponseEntity.ok(cookie)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("logout")
    fun logout(
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok("Logout successful")
    }
}