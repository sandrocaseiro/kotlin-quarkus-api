package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import java.math.BigDecimal
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository: PanacheRepositoryBase<EUser, Int> {
    fun updateBalance(id: Int, balance: BigDecimal): Int =
        update("balance = :balance where id = :id", mapOf("id" to id, "balance" to balance))

    fun findByUsername(username: String): EUser? = find("email", username).firstResult()

    fun findOneById(id: Int): JUserGroup? =
        find("id", id)
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

    //REMARKS: Page queries don't work with fetch joins
    fun findAllActive(sort: Sort?, page: Page): PanacheQuery<EUser> {
        val queryString = "from User u where u.active = true"
        val query: PanacheQuery<EUser> =
            if (sort != null) find(queryString, sort) else find(queryString)
        return query.page(page)
    }
}
