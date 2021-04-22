package dev.sandrocaseiro.template.controllers

import dev.sandrocaseiro.template.mappers.toDCepResp
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.api.ACep
import dev.sandrocaseiro.template.models.dto.DCepResp
import dev.sandrocaseiro.template.models.dto.DResponseDCepResp
import dev.sandrocaseiro.template.services.CepService
import io.quarkus.security.Authenticated
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.enterprise.context.RequestScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Authenticated
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
@Tag(name = "Cep", description = "Cep Operations")
class CepController(
    private val cepService: CepService
) {
    @GET
    @Path("/v1/cep/{cep}")
    @Operation(summary = "CEP search", description = "Search an CEP address")
    @APIResponses(value = [
        APIResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = DResponseDCepResp::class))]),
        APIResponse(responseCode = "404", description = "Not found", content = [Content(schema = Schema(implementation = DResponse::class))]),
        APIResponse(responseCode = "500", description = "Server error", content = [Content(schema = Schema(implementation = DResponse::class))])
    ])
    fun getCep(@Parameter(description = "Cep value", `in` = ParameterIn.PATH, required = true, example = "99999999") @PathParam("cep") cep: String): DCepResp {
        val cepApi: ACep = cepService.searchCep(cep)

        return cepApi.toDCepResp()
    }
}
