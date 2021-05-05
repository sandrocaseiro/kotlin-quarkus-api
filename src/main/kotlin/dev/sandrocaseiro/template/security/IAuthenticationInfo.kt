package dev.sandrocaseiro.template.security

interface IAuthenticationInfo {
    val isAuthenticated: Boolean

    val id: Int

    val groupId: Int
}
