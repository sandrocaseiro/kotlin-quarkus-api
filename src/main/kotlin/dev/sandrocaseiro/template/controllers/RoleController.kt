package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toListDRoleResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.dto.DRoleResp
import dev.sandrocaseiro.template.services.RoleService
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.enterprise.context.RequestScoped
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "Role", description = "Role Operations")
class RoleController(
    private val roleService: RoleService
) {
    @Path("/v1/roles")
    @Operation(summary = "Get all roles", description = "Get all roles")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(): List<DRoleResp> {
        val roles: List<ERole> = roleService.findAll()

        return roles.toListDRoleResp()
    }
}
