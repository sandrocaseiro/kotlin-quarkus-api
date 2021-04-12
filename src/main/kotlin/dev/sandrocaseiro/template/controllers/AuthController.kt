package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toDAuthTokenResp
import dev.sandrocaseiro.template.models.dto.DAuthTokenResp
import dev.sandrocaseiro.template.models.services.SAuth
import dev.sandrocaseiro.template.services.JwtAuthService
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
    fun token(@FormParam("username") username: String, @FormParam("password") password: String): DAuthTokenResp {
        val auth: SAuth = jwtAuthService.authenticate(username, password)

        return auth.toDAuthTokenResp()
    }
}
