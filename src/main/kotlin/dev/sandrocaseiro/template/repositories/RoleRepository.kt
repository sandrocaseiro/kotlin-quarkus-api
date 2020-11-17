package dev.sandrocaseiro.template.repositories

import dev.sandrocaseiro.template.models.domain.ERole
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RoleRepository: PanacheRepositoryBase<ERole, Int>
