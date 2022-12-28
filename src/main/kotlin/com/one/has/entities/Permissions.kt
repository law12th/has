package com.one.has.entities

import jakarta.persistence.*

@Entity
@Table(name = "permissions", schema = "public")
class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "can_create_patient")
    var canCreatePatient: Boolean = false

    @Column(name = "can_create_doctor")
    var canCreateDoctor: Boolean = false

    @Column(name = "can_create_appointment")
    var canCreateAppointment: Boolean = false

    @Column(name = "can_approve_appointment")
    var canApproveAppointment: Boolean = false

    @Column(name = "can_change_appointment")
    var canChangeAppointment: Boolean = false

    @Column(name = "can_register_account")
    var canRegisterAccount: Boolean = false

    @Column(name = "can_login")
    var canLogin: Boolean = false

    @Column(name = "can_delete_account")
    var canDeleteAccount: Boolean = false

    @OneToOne(mappedBy = "permissions")
    var role: Role? = null
}