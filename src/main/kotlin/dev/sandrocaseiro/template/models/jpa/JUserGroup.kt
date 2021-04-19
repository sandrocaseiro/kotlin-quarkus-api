package dev.sandrocaseiro.template.models.jpa

import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class JUserGroup(
    val id: Int,
    val name: String,
    val email: String,
    val group: String
)
