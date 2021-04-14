package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import io.quarkus.security.AuthenticationFailedException
import org.jboss.logging.Logger
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationFailedExceptionMapper: ExceptionMapper<AuthenticationFailedException> {
    private val logger: Logger = Logger.getLogger(AuthenticationFailedException::class.java)
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: AuthenticationFailedException): Response {
        logger.error("AuthenticationFailedExceptionMapper", exception)
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

        return AppException.of(AppErrors.INVALID_TOKEN_ERROR).toResponse(messageSource)
    }
}
