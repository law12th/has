package com.one.has.services

import com.one.has.dtos.RoleDTO
import com.one.has.entities.Role
import com.one.has.repositories.PermissionsRepository
import com.one.has.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService(
    @Autowired
    private val roleRepository: RoleRepository,
    @Autowired
    private val permissionsRepository: PermissionsRepository
) {
    fun getAllRoles(): MutableList<Role> {
        return roleRepository.findAll()
    }

    fun saveRole(role: RoleDTO): RoleDTO {
        val roleEntity = convertToEntity(role)
        val savedRole = roleRepository.save(roleEntity)

        return convertToDTO(savedRole)
    }

    private fun convertToDTO(role: Role): RoleDTO {
        val roleDTO = RoleDTO()

        roleDTO.id = role.id
        roleDTO.name = role.name
        roleDTO.permissionId = role.permissions.id

        return roleDTO
    }

    private fun convertToEntity(roleDTO: RoleDTO): Role {
        val role = Role()

        role.id = roleDTO.id
        role.name = roleDTO.name
        role.permissions = permissionsRepository.findById(roleDTO.permissionId).get()

        return role
    }
}