package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import java.math.BigDecimal
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<EUser, Int> {
    fun findByUsername(username: String): EUser? = find("email", username).firstResult()

    fun updateBalance(id: Int, balance: BigDecimal): Int =
        update("balance = :balance where id = :id", mapOf("id" to id, "balance" to balance))
}
