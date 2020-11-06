package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.repositories.UserRepository
import javax.enterprise.context.RequestScoped
import javax.transaction.Transactional

@RequestScoped
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun create(user: EUser): EUser {
        if (userRepository.findByUsername(user.email) != null)
            AppErrors.USERNAME_ALREADY_EXISTS.throws()

        user.active = true
        userRepository.persist(user)

        return user;
    }
}
