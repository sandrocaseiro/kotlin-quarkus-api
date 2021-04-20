package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import javax.ws.rs.ext.Provider

@Provider
class GlobalExceptionMapper: BaseExceptionMapper<Exception>() {
    override val error: AppErrors = AppErrors.SERVER_ERROR
}
