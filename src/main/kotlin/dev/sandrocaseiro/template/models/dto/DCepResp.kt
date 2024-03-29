package dev.sandrocaseiro.template.models.dto

import dev.sandrocaseiro.template.models.DResponse
import org.eclipse.microprofile.openapi.annotations.media.Schema

data class DCepResp(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val uf: String
)

@Schema(name = "DResponse<DCepResp>", description = "Model to return cep")
class DResponseDCepResp(errors: List<Error>, data: DCepResp) : DResponse<DCepResp>(errors, data)
