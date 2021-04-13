package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import io.quarkus.arc.ArcUndeclaredThrowableException
import org.jboss.logging.Logger
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class InvalidTokenExceptionMapper: ExceptionMapper<ArcUndeclaredThrowableException> {
    private val logger: Logger = Logger.getLogger(InvalidTokenExceptionMapper::class.java)
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: ArcUndeclaredThrowableException): Response {
        logger.error("InvalidTokenException", exception)
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

        return return AppException.of(AppErrors.INVALID_TOKEN_ERROR).toResponse(messageSource)
    }
}
