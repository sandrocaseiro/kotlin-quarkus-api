package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toCreateResp
import dev.sandrocaseiro.template.mappers.toEUser
import dev.sandrocaseiro.template.mappers.toListReport
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.DResponseDUserCreateResp
import dev.sandrocaseiro.template.models.dto.DUserCreateReq
import dev.sandrocaseiro.template.models.dto.DUserReportResp
import dev.sandrocaseiro.template.security.UserPrincipal
import dev.sandrocaseiro.template.services.UserService
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.enterprise.context.RequestScoped
import javax.validation.Valid
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "User", description = "User Operations")
class UserController(
    private val userService: UserService,
    private val userPrincipal: UserPrincipal?
) {
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

//    @PutMapping("/v1/users/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @Operation(summary = "Update User", description = "Update an user", responses = [
//        ApiResponse(responseCode = "204", description = "Updated"),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun updateUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
//                   @RequestBody @Valid dto: DUserUpdateReq,
//                   bindingErrors: Errors) {
//        if (bindingErrors.hasErrors())
//            throw BindValidationException(bindingErrors)
//
//        userService.update(dto.toEUser(id))
//    }
//
//    @PatchMapping("/v1/users/{id}/balance")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("isGroup(1)")
//    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = [
//        ApiResponse(responseCode = "204", description = "Updated"),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun updateUserBalance(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
//                          @RequestBody @Valid dto: DUserBalanceUpdateReq,
//                          bindingErrors: Errors) {
//        if (bindingErrors.hasErrors())
//            throw BindValidationException(bindingErrors)
//
//        userService.updateBalance(id, dto.balance!!)
//    }
//
//    @PatchMapping("/v2/users/{id}/balance")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("isGroup(1)")
//    @Operation(summary = "Update User's balance", description = "Update an user's balance", responses = [
//        ApiResponse(responseCode = "204", description = "Updated"),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun updateUserBalanceApi(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int,
//                             @RequestBody @Valid dto: DUserBalanceUpdateReq,
//                             bindingErrors: Errors) {
//        if (bindingErrors.hasErrors())
//            throw BindValidationException(bindingErrors)
//
//        userService.updateBalanceApi(id, dto.balance!!)
//    }
//
//    @DeleteMapping("/v1/users/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @Operation(summary = "Delete user", description = "Delete an user", responses = [
//        ApiResponse(responseCode = "204", description = "Deleted"),
//        ApiResponse(responseCode = "403", description = "Forbidden", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun deleteUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int) {
//        userService.delete(id)
//    }
//
//    @GetMapping("/v1/users")
//    @Operation(summary = "Search users", description = "Search users by name", responses = [
//        ApiResponse(responseCode = "200", description = "OK"),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun searchUsers(@Parameter(description = "Search string", `in` = ParameterIn.QUERY, example = "user1")
//                    @RequestParam("\$search") search: String?): List<DUserGroupResp> {
//        val users: List<EUser> = userService.searchByName(search ?: "")
//        return users.toListGroupDto()
//    }
//
//    //Sensitive search
//    @PostMapping("/v1/users/search")
//    @Operation(summary = "Search users", description = "Search users using sensitive information", responses = [
//        ApiResponse(responseCode = "200", description = "OK"),
//        ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun searchUsersSensitive(@RequestBody @Valid search: DSearchReq,
//                             bindingErrors: Errors): List<DUserGroupResp> {
//        if (bindingErrors.hasErrors())
//            throw BindValidationException(bindingErrors)
//
//        val users: List<EUser> = userService.searchByCpf(search.searchContent!!)
//        return users.toListGroupDto()
//    }
//
//    //Get token user
//    @GetMapping("/v1/users/current")
//    @Operation(summary = "Get token's user", description = "Get current token's user information", responses = [
//        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun findUser(@AuthenticationPrincipal principal: UserPrincipal): DUserGroupResp {
//        val user: JUserGroup = userService.findById(principal.id)
//
//        return user.toGroupDto()
//    }
//
//    @GetMapping("/v1/users/{id}")
//    @PreAuthorize("canAccessUser(#id)")
//    @Operation(summary = "Get user", description = "Get user by Id", responses = [
//        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun findUser(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): DUserGroupResp {
//        val user: JUserGroup = userService.findById(id)
//
//        return user.toGroupDto()
//    }
//
//    @GetMapping("/v2/users/{id}")
//    @PreAuthorize("canAccessUser(#id)")
//    @Operation(summary = "Get user", description = "Get user by Id", responses = [
//        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDUserGroupResp::class))]),
//        ApiResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun findUserApi(@Parameter(description = "User's id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): DUserResp {
//        val user: SUser = userService.findByIdApi(id)
//
//        return user.toUserResp()
//    }
//
//    @GetMapping("/v1/users/group/{id}")
//    @Operation(summary = "Get users by group", description = "Get all users by group id", responses = [
//        ApiResponse(responseCode = "200", description = "OK"),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun findAllByGroup(@Parameter(description = "Group Id", `in` = ParameterIn.PATH, required = true, example = "1") @PathVariable id: Int): List<DUserResp> {
//        val users: List<EUser> = userService.findByGroup(id)
//
//        return users.toListUserDto()
//    }
//
//    @GetMapping("/v1/users/active")
//    @Operation(summary = "Get all active users", description = "Get all active users with paging", responses = [
//        ApiResponse(responseCode = "200", description = "OK"),
//        ApiResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
//    ])
//    fun findAll(pageable: DPageable): DPage<DUserGroupResp> {
//        val users: Page<EUser> = userService.findAllActive(pageable.asPageable())
//
//        return users.toDto { it.toGroupDto() }
//    }
//
    @GET
    @Path("/v1/users/report")
    @Operation(summary = "Get all users", description = "Get a report for all users")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK"),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun usersReport(): List<DUserReportResp> {
        val users: List<EUser> = userService.findAll()
        val t = userPrincipal?.email

        return users.toListReport("USD")
    }
}
