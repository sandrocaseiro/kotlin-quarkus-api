package dev.sandrocaseiro.template.exceptions.mappers

import dev.sandrocaseiro.template.exceptions.PageableBadRequestException
import org.jboss.logging.Logger
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class PageableBadRequestExceptionMapper: BaseExceptionMapper<PageableBadRequestException>() {
    override val logger: Logger = Logger.getLogger(PageableBadRequestExceptionMapper::class.java)

    override fun toResponse(exception: PageableBadRequestException): Response {
        logger.error("Exception Handler", exception)

        return toResponse(exception.error, messageSource.pageableRequest(exception.fieldName))
    }
}
