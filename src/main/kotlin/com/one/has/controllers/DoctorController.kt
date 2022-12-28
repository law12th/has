package com.one.has.controllers

import com.one.has.dtos.AppointmentDTO
import com.one.has.services.AppointmentService
import com.one.has.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/doctor")
class DoctorController(
    @Autowired
    private val appointmentService: AppointmentService
){
    @PostMapping("accept-appointment")
    fun acceptAppointment(
        @RequestBody
        appointmentDetails: AppointmentDTO
    ): ResponseEntity<AppointmentDTO> {
       appointmentDetails.approval = true

       return ResponseEntity.ok(appointmentService.updateAppointment(appointmentDetails))
    }

    @PatchMapping("change-appointment")
    fun changeAppointment(
        @RequestBody
        appointmentDetails: AppointmentDTO
    ): ResponseEntity<AppointmentDTO> {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentDetails))
    }

    @GetMapping("get-appointments")
    fun getActiveAppointments(
        @RequestBody
        doctorId: Long
    ): ResponseEntity<MutableList<AppointmentDTO>> {
       return ResponseEntity.ok(appointmentService.findAppointmentByDoctorId(doctorId))
    }
}