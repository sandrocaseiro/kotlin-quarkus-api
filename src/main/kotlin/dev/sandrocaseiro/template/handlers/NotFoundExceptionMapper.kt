package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import javax.ws.rs.NotFoundException
import javax.ws.rs.ext.Provider

@Provider
class NotFoundExceptionMapper: BaseExceptionMapper<NotFoundException>() {
    override val error: AppErrors = AppErrors.NOT_FOUND_ERROR
}
