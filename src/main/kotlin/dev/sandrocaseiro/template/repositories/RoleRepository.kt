package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.ERole
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleRepository: PanacheRepositoryBase<ERole, Int> {
    fun findAllByUserId(id: Int): List<ERole> = find("select u.roles from User u where u.id = :id", mapOf("id" to id)).list()
}
