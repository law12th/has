package com.one.has.repositories

import com.one.has.entities.Permissions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionsRepository: JpaRepository<Permissions, Int>
