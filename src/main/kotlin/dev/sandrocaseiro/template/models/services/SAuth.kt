package dev.sandrocaseiro.template.models.services

data class SAuth(
    val tokenType: String,
    val expiresIn: Long,
    val refreshExpiresIn: Long,
    val accessToken: String,
    val refreshToken: String
)
