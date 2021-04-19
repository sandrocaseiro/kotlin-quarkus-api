package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.repositories.RoleRepository
import javax.enterprise.context.RequestScoped

@RequestScoped
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun findAll(): List<ERole> = roleRepository.listAll()
}
