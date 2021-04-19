package dev.sandrocaseiro.template.models.dto

import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Model to return user")
data class DUserResp (
    @Schema(description = "User's id", required = true, example = "1")
    val id: Int,
    @Schema(description = "User's name", required = true, example = "user1")
    val name: String,
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    val email: String
)
