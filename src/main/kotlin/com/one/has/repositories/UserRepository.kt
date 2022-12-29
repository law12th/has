package com.one.has.repositories

import com.one.has.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun findByFirstName(firstName: String): User?

    @Query("SELECT u FROM User u WHERE u.role.id = :roleId")
    fun findAllByRole(roleId: Long): MutableList<User>
}
