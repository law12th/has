package com.one.has.dtos

data class UserDTO(
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var dateOfBirth: String = "",
    var password: String = "",
    var roleId: Int = 0
)
