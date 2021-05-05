package dev.sandrocaseiro.template.security

class AuthenticationInfo(
    override val isAuthenticated: Boolean,
    private val userPrincipal: UserPrincipal? = null
) : IAuthenticationInfo {
    override val id: Int
        get() = userPrincipal?.id ?: 0

    override val groupId: Int
        get() = userPrincipal?.groupId ?: 0
}
