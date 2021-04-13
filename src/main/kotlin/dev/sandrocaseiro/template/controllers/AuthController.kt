package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toDAuthTokenResp
import dev.sandrocaseiro.template.models.dto.DAuthTokenResp
import dev.sandrocaseiro.template.models.services.SAuth
import dev.sandrocaseiro.template.services.JwtAuthService
import dev.sandrocaseiro.template.validations.NotEmpty
import javax.enterprise.context.RequestScoped
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
class AuthController(
    private val jwtAuthService: JwtAuthService
) {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/v1/token")
    fun token(@FormParam("username") @NotEmpty username: String,
              @FormParam("password") @NotEmpty password: String): DAuthTokenResp {
        val auth: SAuth = jwtAuthService.authenticate(username, password)

        return auth.toDAuthTokenResp()
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/v1/refresh-token")
    fun refreshToken(@FormParam("refreshToken") @NotEmpty refreshToken: String): DAuthTokenResp {
        val auth: SAuth = jwtAuthService.authenticateFromRefresh(refreshToken)

        return auth.toDAuthTokenResp()
    }
}
