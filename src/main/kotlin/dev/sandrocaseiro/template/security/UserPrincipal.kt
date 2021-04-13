package dev.sandrocaseiro.template.security

data class UserPrincipal (
    val id: Int,
    val name: String,
    val email: String,
    val groupId: Int,
    val roles: Set<Int>
)
