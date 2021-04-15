package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.services.SAuth
import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.repositories.RoleRepository
import dev.sandrocaseiro.template.repositories.UserRepository
import dev.sandrocaseiro.template.security.UserPrincipal
import io.smallrye.jwt.auth.principal.JWTParser
import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.enterprise.context.RequestScoped
import javax.json.JsonNumber
import javax.json.JsonValue

@RequestScoped
class JwtAuthService(
    private val jwtProps: JwtProperties,
    private val jwtParser: JWTParser,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    companion object {
        private const val USER_ID_CLAIM = "userId"
        private const val NAME_CLAIM = "name"
        private const val GROUP_ID_CLAIM = "groupId"
        private const val ROLES_CLAIM = "roles"
        private const val TOKEN_REFRESH_CLAIM = "isRefresh"
    }

    fun authenticate(username: String, password: String): SAuth {
        val user: EUser? = userRepository.findByUsername(username)
        if (user == null || user.password != password)
            AppErrors.INVALID_CREDENTIALS.throws()

       return generateTokens(user)
    }

    fun authenticateFromRefresh(refreshToken: String): SAuth {
        val jwt: JsonWebToken = jwtParser.parse(refreshToken)
        if (!isRefreshToken(jwt))
            AppErrors.INVALID_TOKEN_ERROR.throws()

        val user: EUser = userRepository.findById(jwt.getClaim<JsonNumber>(USER_ID_CLAIM).intValue()) ?:
            AppErrors.ITEM_NOT_FOUND_ERROR.throws()

        return generateTokens(user)
    }

    fun parseToken(jwt: JsonWebToken): UserPrincipal {
        return UserPrincipal(
            id = jwt.getClaim<JsonNumber>(USER_ID_CLAIM).intValue(),
            name = jwt.getClaim(NAME_CLAIM),
            email = jwt.subject,
            groupId = jwt.getClaim<JsonNumber>(GROUP_ID_CLAIM).intValue(),
            roles = jwt.getClaim<String>(ROLES_CLAIM).split(",").map { it.toInt() }.toSet()
        )
    }

    private fun generateTokens(user: EUser): SAuth {
        val roles: List<String> = roleRepository.findAllByUserId(user.id).map { it.id.toString() }

        val token: String = generateBearerToken(user, roles)
        val tokenRefresh: String = generateRefreshToken(user)

        return SAuth(
            "bearer",
            jwtProps.expiration * 1000,
            jwtProps.refreshExpiration * 1000,
            token,
            tokenRefresh)
    }

    private fun generateBearerToken(user: EUser, roles: List<String>): String {
        val tokenBuilder = Jwt
            .claims()
            .subject(user.email)
            .expiresIn(jwtProps.refreshExpiration)
            .claim(USER_ID_CLAIM, user.id)
            .claim(NAME_CLAIM, user.name)
            .claim(GROUP_ID_CLAIM, user.groupId)
            .claim(ROLES_CLAIM, roles.joinToString(","))
            .jws()

        return tokenBuilder.sign()
    }

    private fun generateRefreshToken(user: EUser): String {
        val tokenBuilder = Jwt
            .claims()
            .subject(user.email)
            .claim(USER_ID_CLAIM, user.id)
            .claim(TOKEN_REFRESH_CLAIM, true)
            .jws()

        return tokenBuilder.sign()
    }

    private fun isRefreshToken(jwt: JsonWebToken): Boolean =
        jwt.containsClaim(TOKEN_REFRESH_CLAIM) && (jwt.getClaim<JsonValue>(TOKEN_REFRESH_CLAIM) as Any).toString().toBoolean()
}
