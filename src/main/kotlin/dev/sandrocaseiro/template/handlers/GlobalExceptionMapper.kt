package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider


@Provider
class GlobalExceptionMapper: ExceptionMapper<Exception> {
    private val logger: Logger = Logger.getLogger(GlobalExceptionMapper::class.java)
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: Exception): Response {
        logger.error(exception)
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

        return exception.toResponse(messageSource.serverError())
    }
}
