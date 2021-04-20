package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.NotFoundException
import javax.ws.rs.NotSupportedException
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotSupportedExceptionMapper: BaseExceptionMapper<NotSupportedException>() {
    override val error: AppErrors = AppErrors.UNSUPPORTED_MEDIA_TYPE
}
