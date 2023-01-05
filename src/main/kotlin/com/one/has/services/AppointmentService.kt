package com.one.has.services

import com.one.has.dtos.AcceptAppointmentDTO
import com.one.has.dtos.AddAppointmentDTO
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
    fun saveAppointment(appointment: AddAppointmentDTO): AppointmentDTO {
        val appointmentDTO = AppointmentDTO()

        appointmentDTO.id = appointment.id
        appointmentDTO.appointmentTime = appointment.appointmentTime
        appointmentDTO.approval = appointment.approval
        appointmentDTO.details = appointment.details
        appointmentDTO.doctorName = userRepository.findById(appointment.doctorId).get().firstName // + " " + userRepository.findById(appointment.doctorId).get().lastName
        appointmentDTO.patientName = userRepository.findById(appointment.patientId).get().firstName // + " " + userRepository.findById(appointment.patientId).get().lastName

        val appointmentEntity = convertToEntity(appointmentDTO)
        val savedAppointment = appointmentRepository.save(appointmentEntity)

        return convertToDTO(savedAppointment)
    }

    fun updateAppointment(appointment: AppointmentDTO): AppointmentDTO {
        val appointmentEntity = convertToEntity(appointment)
        val savedAppointment = appointmentRepository.save(appointmentEntity)

        return convertToDTO(savedAppointment)
    }

    fun acceptAppointment(appointment: AcceptAppointmentDTO) {
        val appointmentDTO = AppointmentDTO()

        appointmentDTO.id = appointment.id
        appointmentDTO.approval = appointment.approval
        appointmentDTO.details = appointmentRepository.findDetailsById(appointment.id)
        appointmentDTO.appointmentTime = appointmentRepository.findAppointmentTimeById(appointment.id)

        val doctorId = appointmentRepository.findDoctorByAppointmentId(appointment.id)
        val patientId = appointmentRepository.findPatientByAppointmentId(appointment.id)

        appointmentDTO.doctorName = userRepository.findById(doctorId).get().firstName
        appointmentDTO.patientName = userRepository.findById(patientId).get().firstName

        val appointmentEntity = convertToEntity(appointmentDTO)
        appointmentRepository.save(appointmentEntity)
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
        appointmentDTO.details = appointment.details
        appointmentDTO.doctorName = appointment.doctor.firstName + " " + appointment.doctor.lastName
        appointmentDTO.patientName = appointment.patient.firstName + " " + appointment.patient.lastName

        return appointmentDTO
    }

    private fun convertToEntity(appointmentDTO: AppointmentDTO): Appointment {
        val appointment = Appointment()

        appointment.id = appointmentDTO.id
        appointment.appointmentTime = appointmentDTO.appointmentTime
        appointment.approval = appointmentDTO.approval
        appointment.details = appointmentDTO.details
        appointment.doctor = userRepository.findByFirstName(appointmentDTO.doctorName)!!
        appointment.patient = userRepository.findByFirstName(appointmentDTO.patientName)!!

        return appointment
    }
}