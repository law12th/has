package com.one.has.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate

@Entity
@Table(name = "user", schema = "public")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    @Column(name = "first_name")
    var firstName: String = ""

    @Column(name = "last_name")
    var lastName: String = ""

    @Column(name = "email", unique = true)
    var email: String = ""

    @Column(name = "date_of_birth")
    var dateOfBirth: String = ""

    @ManyToOne
    @JoinColumn(name = "role_id")
    var role: Role = Role()

    @Column(name = "password", nullable = false)
    var password: String = ""
        @JsonIgnore
        get
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    fun comparePassword(password: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return passwordEncoder.matches(password, this.password)
    }
}
