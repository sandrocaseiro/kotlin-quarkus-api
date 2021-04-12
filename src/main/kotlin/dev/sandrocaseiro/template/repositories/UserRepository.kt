package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<EUser, Int> {
    fun findByUsername(username: String): EUser? = find("email", username).firstResult()
}
