package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import io.quarkus.arc.ArcUndeclaredThrowableException
import io.quarkus.security.UnauthorizedException
import io.smallrye.jwt.auth.principal.ParseException
import org.jboss.logging.Logger
import org.jboss.resteasy.spi.ApplicationException
import org.jose4j.jwt.consumer.InvalidJwtException
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class QuarkusApplicationExceptionMapper: ExceptionMapper<ApplicationException> {
    private val logger: Logger = Logger.getLogger(QuarkusApplicationExceptionMapper::class.java)
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: ApplicationException): Response {
        logger.error("QuarkusApplicationExceptionMapper", exception)
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

        val appError = when (exception.cause) {
            is UnauthorizedException -> AppErrors.UNAUTHORIZED_ERROR
            is ArcUndeclaredThrowableException -> treatArcException(exception.cause as ArcUndeclaredThrowableException)
            else -> AppErrors.SERVER_ERROR
        }

        return AppException.of(appError).toResponse(messageSource)
    }

    private fun treatArcException(exception: ArcUndeclaredThrowableException): AppErrors =
        when (exception.cause) {
            is ParseException -> treatJwtException(exception.cause as ParseException)
            else -> AppErrors.SERVER_ERROR
        }

    private fun treatJwtException(exception: ParseException): AppErrors =
        when (exception.cause) {
            is InvalidJwtException -> AppErrors.INVALID_TOKEN_ERROR
            else -> AppErrors.SERVER_ERROR
        }
}
