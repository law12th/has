package com.one.has.repositories

import com.one.has.entities.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository: JpaRepository<Appointment, Int> {
    fun findAllByDoctorId(doctorId: Long): MutableList<Appointment>

    fun findAllByPatientId(patientId: Long): MutableList<Appointment>

    @Query("SELECT a.details FROM Appointment a WHERE a.id = :id")
    fun findDetailsById(id: Int): String

    @Query("SELECT a.appointmentTime FROM Appointment a WHERE a.id = :id")
    fun findAppointmentTimeById(id: Int): String

    @Query("SELECT a.doctor.id FROM Appointment a WHERE a.id = :id")
    fun findDoctorByAppointmentId(id: Int): Long

    @Query("SELECT a.patient.id FROM Appointment a WHERE a.id = :id")
    fun findPatientByAppointmentId(id: Int): Long
}
