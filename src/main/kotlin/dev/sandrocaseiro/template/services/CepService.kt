package dev.sandrocaseiro.template.services

import com.fasterxml.jackson.databind.JsonNode
import dev.sandrocaseiro.template.clients.CepClient
import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.models.api.ACep
import dev.sandrocaseiro.template.utils.deserialize
import dev.sandrocaseiro.template.utils.deserializeTree
import dev.sandrocaseiro.template.utils.isError
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.InputStream
import javax.enterprise.context.RequestScoped
import javax.ws.rs.core.Response

@RequestScoped
class CepService(
    @RestClient private val cepClient: CepClient
) {
    fun searchCep(cep: String): ACep {
        val resp: Response = cepClient.buscarEnderecoPorCep(cep)

        if (Response.Status.fromStatusCode(resp.status).isError())
            AppErrors.API_ERROR.throws()

        val node: JsonNode? = (resp.entity as InputStream?).deserializeTree()
        if (node == null || node.has("erro"))
            AppErrors.ITEM_NOT_FOUND_ERROR.throws()

        return node.deserialize()
    }
}
