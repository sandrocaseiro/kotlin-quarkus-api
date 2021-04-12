package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.dto.DAuthTokenResp
import dev.sandrocaseiro.template.models.services.SAuth

fun SAuth.toDAuthTokenResp() = DAuthTokenResp(
    tokenType = this.tokenType,
    expiresIn = this.expiresIn,
    refreshExpiresIn = this.refreshExpiresIn,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
