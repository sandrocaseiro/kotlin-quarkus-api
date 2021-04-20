package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.utils.toResponse
import org.jboss.logging.Logger
import javax.ws.rs.NotAcceptableException
import javax.ws.rs.NotAllowedException
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class NotAcceptableExceptionMapper: BaseExceptionMapper<NotAcceptableException>() {
    override val error: AppErrors = AppErrors.NOT_ACCEPTABLE_MEDIA_TYPE
}
