package dev.sandrocaseiro.template.clients

import dev.sandrocaseiro.template.clients.factories.CepApiHeaderFactory
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

@RegisterRestClient(configKey = "cep-api")
@RegisterClientHeaders(CepApiHeaderFactory::class)
interface CepClient {
    @GET
    @Path("/{cep}/json/")
    fun buscarEnderecoPorCep(@PathParam("cep") cep: String): Response
}
