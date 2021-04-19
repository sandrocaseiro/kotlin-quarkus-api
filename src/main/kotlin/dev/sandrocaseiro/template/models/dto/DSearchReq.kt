package dev.sandrocaseiro.template.models.dto

import dev.sandrocaseiro.template.validations.NotEmpty
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Schema(description = "Model to search using sensitive information")
data class DSearchReq (
    @NotEmpty
    @Schema(description = "Search string", required = true, example = "29035196090")
    val searchContent: String?
)
