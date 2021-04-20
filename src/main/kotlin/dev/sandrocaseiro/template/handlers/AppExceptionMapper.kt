package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class AppExceptionMapper : BaseExceptionMapper<AppException>() {
    override val logger: Logger = Logger.getLogger(AppExceptionMapper::class.java)

    override fun toResponse(exception: AppException): Response {
        return exception.toResponse(messageSource)
    }
}
