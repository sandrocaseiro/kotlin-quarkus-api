package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.repositories.RoleRepository
import dev.sandrocaseiro.template.repositories.UserRepository
import dev.sandrocaseiro.template.security.TokenAuthResponse
import dev.sandrocaseiro.template.security.TokenUser
import io.smallrye.jwt.build.Jwt
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.enterprise.context.RequestScoped

@RequestScoped
class JwtAuthService(
    private val jwtProps: JwtProperties,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    private val TOKEN_REFRESH_HEADER_KEY = "refresh"

    fun generateTokenResponse(user: TokenUser): TokenAuthResponse {
        val roles: List<String> = user.roles.map { it.toString() }

        val expirationTime: Long = jwtProps.expiration
        val refreshExpirationTime: Long = jwtProps.refreshExpiration

        val token: String = generateBearerToken(user, roles, expirationTime, false)
        val tokenRefresh: String = generateBearerToken(user, roles, refreshExpirationTime, true)

        return TokenAuthResponse(
            "bearer",
            expirationTime,
            refreshExpirationTime,
            token,
            tokenRefresh)
    }

    private fun generateBearerToken(user: TokenUser, roles: List<String>, expirationTime: Long, isRefresh: Boolean): String {
        val expiration = LocalDateTime.now().plus(expirationTime, ChronoUnit.MILLIS)
        val tokenBuilder = Jwt
            .claims()
            .subject(user.email)
            .claim("userId", user.id)
            .claim("name", user.name)
            .claim("groupId", user.groupId)
            .claim("roles", roles.joinToString(","))
            .expiresAt(expiration.atZone(ZoneId.systemDefault()).toInstant())
            .jws()

//        val tokenBuilder: JwtBuilder = Jwts.builder()
//            .setHeaderParam("typ", "JWT")
//            .setSubject(user.username)
//            .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
//            .signWith(Keys.hmacShaKeyFor(jwtProps.secret.toByteArray(StandardCharsets.UTF_8)))
//            .claim("userId", user.id)
//            .claim("name", user.name)
//            .claim("groupId", user.groupId)
//            .claim("roles", roles.joinToString(","))

        if (isRefresh)
            tokenBuilder.header(TOKEN_REFRESH_HEADER_KEY, true)

        return tokenBuilder.signWithSecret(jwtProps.secret)
    }

    fun parseBearerToken(token: String): TokenUser {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.secret.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .body

        val userId = claims.get("userId", Integer::class.java)
        val name = claims.get("name", String::class.java)
        val email: String = claims.subject
        val groupId = claims.get("groupId", Integer::class.java)
        val roles: List<Int> = claims.get("roles", String::class.java).split(",")
            .map { it.toInt() }

        return TokenUser(userId.toInt(), name, email, "", groupId.toInt(), roles)
    }

    fun isRefreshToken(token: String): Boolean {
        val header: JwsHeader<out JwsHeader<*>> = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.secret.toByteArray(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .header ?: return false

        return header.getOrDefault(TOKEN_REFRESH_HEADER_KEY, false) as Boolean
    }

//    override fun loadUserByUsername(username: String): UserDetails {
//        val user: EUser = userRepository.findByUsernameActive(username) ?: AppErrors.INVALID_CREDENTIALS.throws()
//
//        val roles: List<Int> = roleRepository.findAllByUserId(user.id).map { it.id }
//
//        return TokenUser(
//            user.id,
//            user.name,
//            username,
//            user.password,
//            user.groupId,
//            roles
//        )
//    }
}