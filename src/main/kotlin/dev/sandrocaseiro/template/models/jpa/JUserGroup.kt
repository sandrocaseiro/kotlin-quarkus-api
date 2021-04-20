package dev.sandrocaseiro.template.models.jpa

import io.quarkus.hibernate.orm.panache.ProjectedFieldName
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class JUserGroup(
    val id: Int,
    val name: String,
    val email: String,
    @ProjectedFieldName("group.name")
    val group: String
)
