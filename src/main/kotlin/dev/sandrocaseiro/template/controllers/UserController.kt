package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toCreateResp
import dev.sandrocaseiro.template.mappers.toEUser
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.DUserCreateReq
import dev.sandrocaseiro.template.models.dto.DUserCreateResp
import dev.sandrocaseiro.template.services.UserService
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class UserController(
    private val userService: UserService
) {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/v1/users/test")
    fun test() = "test"

    @POST
    @Path("/v1/users")
    fun createUser(dto: DUserCreateReq): DUserCreateResp {
        val user: EUser = userService.create(dto.toEUser())
        return user.toCreateResp()
    }
}
