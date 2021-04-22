package dev.sandrocaseiro.template.models.dto

import dev.sandrocaseiro.template.validations.Cpf
import dev.sandrocaseiro.template.validations.NotEmpty
import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.validation.constraints.*

@Schema(description = "Model for user creation")
data class DUserCreateReq (
    @get:NotEmpty
    @get:Size(max = 50)
    @Schema(description = "User's name", required = true, example = "user1")
    val name: String?,

    @get:NotEmpty
    @get:Size(max = 150)
    @get:Email
    @Schema(description = "User's e-mail", required = true, example = "user1@mail.com")
    val email: String?,

    @get:Cpf
    @get:NotEmpty
    @get:Size(max = 11)
    @Schema(description = "User's CPF", required = true, example = "29035196090")
    val cpf: String?,

    @get:NotEmpty
    @get:Size(max = 20)
    @Schema(description = "User's password", required = true, example = "1234")
    val password: String?,

    @get:NotEmpty
    @get:Min(1)
    @Schema(description = "User's Group Id", required = true, example = "1")
    val groupId: Int?,

    @get:NotEmpty
    @Schema(description = "User's Role id's", required = true, example = "[1,2]")
    val roles: List<Int>?
)
