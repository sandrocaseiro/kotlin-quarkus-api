package dev.sandrocaseiro.template.models.dto

import dev.sandrocaseiro.template.validations.NotEmpty
import org.eclipse.microprofile.openapi.annotations.media.Schema
import java.math.BigDecimal
import javax.validation.constraints.Min

@Schema(description = "Model for updating user's balance")
data class DUserBalanceUpdateReq (
    @get:NotEmpty @get:Min(0)
    @Schema(description = "User's new balance", required = true, example = "55.79")
    val balance: BigDecimal? = null
)
