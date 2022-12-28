package com.one.has.controllers

import com.one.has.dtos.UserDTO
import com.one.has.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/admin")
class AdminController(
    @Autowired
    private val userService: UserService
){
    @PostMapping("add-doctor")
    fun addDoctor(
        @RequestBody
        doctorRegistrationDetails: UserDTO
    ): ResponseEntity<UserDTO> {
        doctorRegistrationDetails.roleId = 1

        return ResponseEntity.ok(userService.saveUser(doctorRegistrationDetails))
    }

    @DeleteMapping("delete-doctor")
    fun removeDoctor(
        @RequestBody
        doctorId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(userService.deleteUser(doctorId))
    }

    @PatchMapping("update-doctor")
    fun updateDoctor(
        @RequestBody
        doctorDetails: UserDTO
    ): ResponseEntity<UserDTO> {
       return ResponseEntity.ok(userService.updateUser(doctorDetails))
    }

    @GetMapping("get-doctors")
    fun getAllDoctors(): ResponseEntity<MutableList<UserDTO>> {
        return ResponseEntity.ok(userService.findAllUsersByRole(1))
    }

    @GetMapping("get-doctor")
    fun getDoctor(
        @RequestBody
        doctorId: Long
    ): ResponseEntity<UserDTO> {
       return ResponseEntity.ok(userService.findUserById(doctorId))
    }
}