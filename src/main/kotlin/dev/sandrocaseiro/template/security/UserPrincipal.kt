package dev.sandrocaseiro.template.security

open class UserPrincipal (
    open val id: Int,
    open val name: String,
    open val email: String,
    open val groupId: Int,
    open val roles: Set<Int>
)
