package com.one.has.dtos

import java.time.LocalDateTime

data class AppointmentDTO(
    var id: Int = 0,
    var appointmentTime: String = "",
    var approval: Boolean = false,
    var doctorName: String = "",
    var patientName: String = ""
)
