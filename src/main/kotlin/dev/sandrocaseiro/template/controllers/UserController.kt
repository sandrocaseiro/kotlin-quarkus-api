package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.*
import dev.sandrocaseiro.template.models.DPage
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.*
import dev.sandrocaseiro.template.models.jpa.JUserGroup
import dev.sandrocaseiro.template.security.IAuthenticationInfo
import dev.sandrocaseiro.template.security.permissions.CanAccessUser
import dev.sandrocaseiro.template.security.permissions.IsGroup
import dev.sandrocaseiro.template.services.UserService
import dev.sandrocaseiro.template.utils.getPageable
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import io.quarkus.security.Authenticated
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import org.jboss.resteasy.spi.HttpRequest
import javax.annotation.security.PermitAll
import javax.enterprise.context.RequestScoped
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Authenticated
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "User", description = "User Operations")
class UserController(
    private val authInfo: IAuthenticationInfo,
    private val userService: UserService
) {
    @PermitAll
    @POST
    @Path("/v1/users")
    @Operation(summary = "Create User", description = "Create a new user")
    @APIResponses(value = [
        APIResponse(responseCode = "201", description = "Created", content = [Content(schema = Schema(implementation = DResponseDUserCreateResp::class))]),
        APIResponse(responseCode = "400", description = "Bad request", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun createUser(@Valid dto: DUserCreateReq): Response {
        val user: EUser = userService.create(dto.toEUser())
        return Response
            .status(Response.Status.CREATED)
            .entity(user.toCreateResp())
            .build()
    }

    @PUT
    @Path("/v1/users/{id}")
    @Operation(summary = "Update User", description = "Update an user")
    @APIResponses(value = [
        APIResponse(responseCode = "204", description = "Updated"),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun updateUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathParam("id") id: Int,
                   @Valid dto: DUserUpdateReq
    ): Response {
        userService.update(dto.toEUser(id))
        return Response
            .status(Response.Status.NO_CONTENT)
            .build()
    }

    @PATCH
    @Path("/v1/users/{id}/balance")
    @IsGroup(1)
    @Operation(summary = "Update User's balance", description = "Update an user's balance")
    @APIResponses(value = [
        APIResponse(responseCode = "204", description = "Updated"),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun updateUserBalance(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathParam("id") id: Int,
                          @Valid dto: DUserBalanceUpdateReq
    ): Response {
        userService.updateBalance(id, dto.balance!!)

        return Response
            .status(Response.Status.NO_CONTENT)
            .build()
    }

    @DELETE
    @Path("/v1/users/{id}")
    @Operation(summary = "Delete user", description = "Delete an user")
    @APIResponses(value = [
        APIResponse(responseCode = "204", description = "Deleted"),
        APIResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun deleteUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") id: Int): Response {
        userService.delete(id)

        return Response
            .status(Response.Status.NO_CONTENT)
            .build()
    }

    @GET
    @Path("/v1/users")
    @Operation(summary = "Search users", description = "Search users by name")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun searchUsers(@Parameter(description = "Search string", `in` = ParameterIn.QUERY, example = "user1")
                    @QueryParam("\$search") search: String?): List<DUserGroupResp> {
        val users: List<EUser> = userService.searchByName(search ?: "")
        return users.toListGroupDto()
    }

    //Sensitive search
    @POST
    @Path("/v1/users/search")
    @Operation(summary = "Search users", description = "Search users using sensitive information")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun searchUsersSensitive(@Valid search: DSearchReq): List<DUserGroupResp> {
        val users: List<EUser> = userService.searchByCpf(search.searchContent!!)
        return users.toListGroupDto()
    }

    //Get token user
    @GET
    @Path("/v1/users/current")
    @Operation(summary = "Get token's user", description = "Get current token's user information")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findUser(): DUserGroupResp {
        val user: JUserGroup = userService.findById(authInfo.id)

        return user.toGroupDto()
    }

    @GET
    @Path("/v1/users/{id}")
    @CanAccessUser("id")
    @Operation(summary = "Get user", description = "Get user by Id")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathParam("id") id: Int): DUserGroupResp {
        val user: JUserGroup = userService.findById(id)

        return user.toGroupDto()
    }

    // REMARKS: Not possible yet to map various parameters to just one object. Using request properties for now
    @GET
    @Path("/v1/users/active")
    @Operation(summary = "Get all active users", description = "Get all active users with paging")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun findAll(@Context req: HttpRequest): DPage<DUserGroupResp> {
        val pageable = req.getPageable()
        val users: PanacheQuery<EUser> = userService.findAllActive(pageable.sort, pageable.page)

        return users.toDto { it.toGroupDto() }
    }

    @GET
    @Path("/v1/users/report")
    @Operation(summary = "Get all users", description = "Get a report for all users")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun usersReport(): List<DUserReportResp> {
        val users: List<EUser> = userService.findAll()

        return users.toListReport("USD")
    }
}
