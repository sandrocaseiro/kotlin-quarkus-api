package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toListDGroupResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.EGroup
import dev.sandrocaseiro.template.models.dto.DGroupResp
import dev.sandrocaseiro.template.services.GroupService
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@PermitAll
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "Group", description = "Group Operations")
class GroupController(
    private val groupService: GroupService
) {
    @Path("/v1/groups")
    @Operation(summary = "Get all groups", description = "Get all groups")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(): List<DGroupResp> {
        val groups: List<EGroup> = groupService.findAll()

        return groups.toListDGroupResp()
    }
}
