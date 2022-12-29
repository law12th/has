package com.one.has.services

import com.one.has.dtos.AppointmentDTO
import com.one.has.entities.Appointment
import com.one.has.repositories.AppointmentRepository
import com.one.has.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AppointmentService(
    @Autowired
    private val appointmentRepository: AppointmentRepository,
    @Autowired
    private val userRepository: UserRepository
) {
    fun saveAppointment(appointment: AppointmentDTO): AppointmentDTO {
        val appointmentEntity = convertToEntity(appointment)
        val savedAppointment = appointmentRepository.save(appointmentEntity)

        return convertToDTO(savedAppointment)
    }

    fun updateAppointment(appointment: AppointmentDTO): AppointmentDTO {
        val appointmentEntity = convertToEntity(appointment)
        val savedAppointment = appointmentRepository.save(appointmentEntity)

        return convertToDTO(savedAppointment)
    }

    fun findAppointmentByDoctorId(doctorId: Long): MutableList<AppointmentDTO> {
        val appointments = appointmentRepository.findAllByDoctorId(doctorId)
        val appointmentDTOs = mutableListOf<AppointmentDTO>()

        for (appointment in appointments) {
            appointmentDTOs.add(convertToDTO(appointment))
        }

        return appointmentDTOs
    }

    fun findAppointmentsByPatientId(patientId: Long): MutableList<AppointmentDTO> {
        val appointments = appointmentRepository.findAllByPatientId(patientId)
        val appointmentDTOs = mutableListOf<AppointmentDTO>()

        for (appointment in appointments) {
            appointmentDTOs.add(convertToDTO(appointment))
        }

        return appointmentDTOs
    }

    private fun convertToDTO(appointment: Appointment): AppointmentDTO {
        val appointmentDTO = AppointmentDTO()

        appointmentDTO.id = appointment.id
        appointmentDTO.appointmentTime = appointment.appointmentTime
        appointmentDTO.approval = appointment.approval
        appointmentDTO.doctorName = appointment.doctor.firstName + " " + appointment.doctor.lastName
        appointmentDTO.patientName = appointment.patient.firstName + " " + appointment.patient.lastName

        return appointmentDTO
    }

    private fun convertToEntity(appointmentDTO: AppointmentDTO): Appointment {
        val appointment = Appointment()

        appointment.id = appointmentDTO.id
        appointment.appointmentTime = appointmentDTO.appointmentTime
        appointment.approval = appointmentDTO.approval
        appointment.doctor = userRepository.findByFirstName(appointmentDTO.doctorName)!!
        appointment.patient = userRepository.findByFirstName(appointmentDTO.patientName)!!

        return appointment
    }
}