package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.services.SAuth
import dev.sandrocaseiro.template.properties.JwtProperties
import dev.sandrocaseiro.template.repositories.RoleRepository
import dev.sandrocaseiro.template.repositories.UserRepository
import io.smallrye.jwt.build.Jwt
import javax.enterprise.context.RequestScoped

@RequestScoped
class JwtAuthService(
    private val jwtProps: JwtProperties,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    private val TOKEN_REFRESH_CLAIM = "isRefresh"

    fun authenticate(username: String, password: String): SAuth {
        val user: EUser? = userRepository.findByUsername(username)
        if (user == null || user.password != password)
            AppErrors.INVALID_CREDENTIALS.throws()

        val roles: List<String> = roleRepository.findAllByUserId(user.id).map { it.id.toString() }
        val refreshExpirationTime: Long = jwtProps.refreshExpiration

        val token: String = generateBearerToken(user, roles)
        val tokenRefresh: String = generateRefreshToken(user)

        return SAuth(
            "bearer",
            jwtProps.expiration * 1000,
            refreshExpirationTime,
            token,
            tokenRefresh)
    }

    private fun generateBearerToken(user: EUser, roles: List<String>): String {
        val tokenBuilder = Jwt
            .claims()
            .subject(user.email)
            .expiresIn(jwtProps.refreshExpiration)
            .claim("userId", user.id)
            .claim("name", user.name)
            .claim("groupId", user.groupId)
            .claim("roles", roles.joinToString(","))
            .jws()

        return tokenBuilder.sign()
    }

    private fun generateRefreshToken(user: EUser): String {
        val tokenBuilder = Jwt
            .claims()
            .subject(user.email)
            .claim("userId", user.id)
            .claim(TOKEN_REFRESH_CLAIM, true)
            .jws()

        return tokenBuilder.sign()
    }

//    fun parseBearerToken(token: String): TokenUser {
//        val claims: Claims = Jwts.parserBuilder()
//            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.secret.toByteArray(StandardCharsets.UTF_8)))
//            .build()
//            .parseClaimsJws(token)
//            .body
//
//        val userId = claims.get("userId", Integer::class.java)
//        val name = claims.get("name", String::class.java)
//        val email: String = claims.subject
//        val groupId = claims.get("groupId", Integer::class.java)
//        val roles: List<Int> = claims.get("roles", String::class.java).split(",")
//            .map { it.toInt() }
//
//        return TokenUser(userId.toInt(), name, email, "", groupId.toInt(), roles)
      //  return TokenUser(0, "", "", "", 1, emptyList())
    //}

    fun isRefreshToken(token: String): Boolean {
//        val header: JwsHeader<out JwsHeader<*>> = Jwts.parserBuilder()
//            .setSigningKey(Keys.hmacShaKeyFor(jwtProps.secret.toByteArray(StandardCharsets.UTF_8)))
//            .build()
//            .parseClaimsJws(token)
//            .header ?: return false
//
//        return header.getOrDefault(TOKEN_REFRESH_HEADER_KEY, false) as Boolean
        return false
    }
}