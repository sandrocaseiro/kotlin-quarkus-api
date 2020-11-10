package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class AppExceptionMapper : ExceptionMapper<AppException> {
    private val logger: Logger = Logger.getLogger(AppExceptionMapper::class.java)
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: AppException): Response {
        logger.error("AppException", exception)
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

        return exception.toResponse(messageSource)
    }
}