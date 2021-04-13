package dev.sandrocaseiro.template.security

import dev.sandrocaseiro.template.services.JwtAuthService
import io.quarkus.security.identity.SecurityIdentity
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Produces

@RequestScoped
class UserPrincipalProducer (
    private val jwtAuthService: JwtAuthService,
    private val identity: SecurityIdentity
){
    @Produces
    @RequestScoped
    fun currentUserPrincipal(): IAuthenticationInfo {
        if (identity.isAnonymous)
            return AuthenticationInfo(isAuthenticated = false)

        if (identity.principal is JsonWebToken) {
            val user = jwtAuthService.parseToken(identity.principal as JsonWebToken)
            return AuthenticationInfo(isAuthenticated = true, user)
        }

        throw IllegalStateException("Invalid user principal")
    }
}
