package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Page
import java.math.BigDecimal
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<EUser, Int> {
    fun updateBalance(id: Int, balance: BigDecimal): Int =
        update("balance = :balance where id = :id", mapOf("id" to id, "balance" to balance))

    fun findByUsername(username: String): EUser? = find("email", username).firstResult()

    fun findOneById(id: Int): JUserGroup? =
        find(
            "select u.id as id, u.name as name, u.email as email, u.group.name as group from User u where u.id = :id",
            mapOf("id" to id)
        )
        .project(JUserGroup::class.java).firstResult()

    fun searchByName(name: String): List<EUser> =
        find(
            "select u from User u inner join fetch u.group g where lower(u.name) like lower(concat('%', :name, '%'))",
            mapOf("name" to name)
        ).list()

    fun searchByCpf(cpf: String): List<EUser> =
        find(
            "select u from User u inner join fetch u.group g where lower(u.cpf) like lower(concat('%', :cpf, '%'))",
            mapOf("cpf" to cpf)
        ).list()

    fun findAllActive(page: Page): PanacheQuery<EUser>
        = find("select u from User u inner join fetch u.group where u.active = true").page(page)

    fun findAllList(): List<EUser> = find("select u from User u inner join fetch u.group").list()
}
