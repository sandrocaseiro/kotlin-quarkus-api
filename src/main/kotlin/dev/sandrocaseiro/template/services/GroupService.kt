package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.models.domain.EGroup
import dev.sandrocaseiro.template.repositories.GroupRepository
import javax.enterprise.context.RequestScoped

@RequestScoped
class GroupService(
    private val groupRepository: GroupRepository
) {
    fun findAll(): List<EGroup> = groupRepository.listAll()
}
