package com.one.has.dtos

import java.time.LocalDateTime

data class AppointmentDTO(
    var id: Int = 0,
    var appointmentTime: String = "",
    var approval: Boolean = false,
    var doctorId: Long = 0,
    var patientId: Long = 0
)
