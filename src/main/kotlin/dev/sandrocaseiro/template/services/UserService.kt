package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.repositories.UserRepository
import dev.sandrocaseiro.template.security.IAuthenticationInfo
import java.math.BigDecimal
import javax.enterprise.context.RequestScoped
import javax.transaction.Transactional

@RequestScoped
class UserService(
    private val authInfo: IAuthenticationInfo,
    private val userRepository: UserRepository
) {
    @Transactional
    fun create(user: EUser): EUser {
        if (userRepository.findByUsername(user.email) != null)
            AppErrors.USERNAME_ALREADY_EXISTS.throws()

        user.active = true
        userRepository.persist(user)
        return user
    }

    @Transactional
    fun update(user: EUser) {
        val dbUser: EUser = userRepository.findById(user.id) ?: AppErrors.ITEM_NOT_FOUND_ERROR.throws()

        dbUser.name = user.name
        dbUser.cpf = user.cpf
        dbUser.password = user.password
        dbUser.groupId = user.groupId
        dbUser.roles = user.roles

        userRepository.persist(dbUser)
    }

    @Transactional
    fun updateBalance(id: Int, balance: BigDecimal) {
        val updatedUsers: Int = userRepository.updateBalance(id, balance)
        if (updatedUsers == 0)
            AppErrors.ITEM_NOT_FOUND_ERROR.throws()
    }

    @Transactional
    fun delete(id: Int) {
        if (authInfo.id == id)
            AppErrors.FORBIDDEN_ERROR.throws()

        val user: EUser = userRepository.findById(id) ?: AppErrors.NOT_FOUND_ERROR.throws()
        user.active = false

        userRepository.persist(user)
    }

//    fun findById(id: Int): JUserGroup = userRepository.findOneById(id) ?: AppErrors.ITEM_NOT_FOUND_ERROR.throws()

//    fun searchByName(name: String): List<EUser> =  userRepository.searchByName(name)
//
//    fun searchByCpf(cpf: String): List<EUser> = userRepository.searchByCpf(cpf)
//
//    fun findByGroup(groupId: Int): List<EUser> = userRepository.findByGroup(groupId)
//
//    fun findAllActive(pageable: Pageable): Page<EUser> = userRepository.findAllActive(pageable)

    fun findAll(): List<EUser> = userRepository.findAll().list()
}
