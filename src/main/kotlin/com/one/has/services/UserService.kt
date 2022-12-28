package com.one.has.services

import com.one.has.dtos.UserDTO
import com.one.has.entities.User
import com.one.has.repositories.RoleRepository
import com.one.has.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val roleRepository: RoleRepository
) {
   fun findUserById(id: Long): UserDTO {
        val user = userRepository.findById(id);

        return convertToDTO(user.get())
   }

    fun findAllUsers(): MutableList<UserDTO> {
        val users = userRepository.findAll()
        val userDTOs = mutableListOf<UserDTO>()

        for (user in users) {
            userDTOs.add(convertToDTO(user))
        }

        return userDTOs
    }

    fun findAllUsersByRole(roleId: Long): MutableList<UserDTO> {
        val users = userRepository.findAllByRole(roleId)
        val userDTOs = mutableListOf<UserDTO>()

        for (user in users) {
            userDTOs.add(convertToDTO(user))
        }

        return userDTOs
    }

    fun findUserByEmail(email: String): UserDTO {
        val user = userRepository.findByEmail(email);

        return convertToDTO(user!!)
    }

    fun saveUser(user: UserDTO): UserDTO {
        val userEntity = convertToEntity(user)
        val savedUser = userRepository.save(userEntity)

        return convertToDTO(savedUser)
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun updateUser(user: UserDTO): UserDTO {
        val userEntity = convertToEntity(user)
        val savedUser = userRepository.save(userEntity)

        return convertToDTO(savedUser)
    }

   fun determineIfUserIsValid(email: String, password: String): Boolean {
       val user = userRepository.findByEmail(email) ?: return false

       if (!user.comparePassword(password)) {
           return false
       }

       return true

   }

   private fun convertToDTO(user: User): UserDTO {
       val userDTO = UserDTO()

       userDTO.id = user.id
       userDTO.firstName = user.firstName
       userDTO.lastName = user.lastName
       userDTO.email = user.email
       userDTO.dateOfBirth = user.dateOfBirth
       userDTO.password = user.password
       userDTO.roleId = user.role.id

       return userDTO
   }

    private fun convertToEntity(userDTO: UserDTO): User {
        val user = User()

        user.id = userDTO.id
        user.firstName = userDTO.firstName
        user.lastName = userDTO.lastName
        user.email = userDTO.email
        user.dateOfBirth = userDTO.dateOfBirth
        user.password = userDTO.password
        user.role = roleRepository.findById(userDTO.roleId).get()

        return user
    }
}