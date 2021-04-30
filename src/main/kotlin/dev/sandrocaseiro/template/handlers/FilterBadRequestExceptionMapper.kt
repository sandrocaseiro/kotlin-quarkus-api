package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.FilterBadRequestException
import org.jboss.logging.Logger
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class FilterBadRequestExceptionMapper: BaseExceptionMapper<FilterBadRequestException>() {
    override val logger: Logger = Logger.getLogger(FilterBadRequestException::class.java)

    override fun toResponse(exception: FilterBadRequestException): Response {
        logger.error("Exception Handler", exception)

        return toResponse(exception.error, messageSource.filterRequest(exception.fieldName))
    }
}
