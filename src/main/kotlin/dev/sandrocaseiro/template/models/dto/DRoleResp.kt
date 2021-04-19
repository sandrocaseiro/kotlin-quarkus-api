package dev.sandrocaseiro.template.models.dto

import org.eclipse.microprofile.openapi.annotations.media.Schema

data class DRoleResp(
    @Schema(description = "Role's id", example = "1")
    val id: Int,
    @Schema(description = "Role's name", example = "role1")
    val name: String
)
