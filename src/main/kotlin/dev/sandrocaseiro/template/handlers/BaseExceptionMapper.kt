package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.AppMessages
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper

abstract class BaseExceptionMapper<T: Throwable>: ExceptionMapper<T> {
    @Context
    lateinit var headers: HttpHeaders

    open val logger: Logger = Logger.getLogger(BaseExceptionMapper::class.java)
    open val error: AppErrors = AppErrors.SERVER_ERROR

    val messageSource: AppMessages get() = LocalizedMessageSource.getAppMessages(headers)

    override fun toResponse(exception: T): Response {
        logger.error("Exception Handler", exception)

        return AppException.of(error).toResponse(messageSource)
    }

    fun toResponse(appError: AppErrors): Response {
        return AppException.of(appError).toResponse(messageSource)
    }

    fun toResponse(appError: AppErrors, errors: List<DResponse.Error>): Response {
        return AppException.of(appError).toResponse(errors)
    }
}
