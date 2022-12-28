package com.one.has.repositories

import com.one.has.entities.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository: JpaRepository<Appointment, Int> {
    fun findAllByDoctorId(doctorId: Long): MutableList<Appointment>

    fun findAllByPatientId(patientId: Long): MutableList<Appointment>
}
