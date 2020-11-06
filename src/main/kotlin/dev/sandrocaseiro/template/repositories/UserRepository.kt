package dev.sandrocaseiro.template.repositories

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import dev.sandrocaseiro.template.models.domain.EUser
import javax.enterprise.context.RequestScoped

@RequestScoped
class UserRepository: PanacheRepository<EUser> {
    fun findByUsername(username: String): EUser? = find("email", username).firstResult()
}
