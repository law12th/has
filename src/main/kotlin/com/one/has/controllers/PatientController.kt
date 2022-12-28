package com.one.has.controllers

import com.one.has.dtos.AppointmentDTO
import com.one.has.services.AppointmentService
import com.one.has.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/patient")
class PatientController(
    @Autowired
    private val userService: UserService,
    @Autowired
    private val appointmentService: AppointmentService
){
    @PostMapping("add-appointment")
    fun addAppointment(
        @RequestBody
        appointmentDetails: AppointmentDTO
    ): ResponseEntity<AppointmentDTO> {
        return ResponseEntity.ok(appointmentService.saveAppointment(appointmentDetails))
    }

    @GetMapping("get-appointments")
    fun getAppointments(
        @RequestBody
        patientId: Long
    ): ResponseEntity<MutableList<AppointmentDTO>> {
        return ResponseEntity.ok(appointmentService.findAppointmentsByPatientId(patientId))
    }
}