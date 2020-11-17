package dev.sandrocaseiro.template.properties

import io.quarkus.arc.config.ConfigProperties

@ConfigProperties(prefix = "jwt")
data class JwtProperties (
    val expiration: Long,
    val refreshExpiration: Long,
    val secret: String,
    val tokenPrefix: String,
    val header: String
)
