package dev.sandrocaseiro.template.exceptions.mappers

import dev.sandrocaseiro.template.exceptions.AppErrors
import javax.ws.rs.NotAcceptableException
import javax.ws.rs.ext.Provider

@Provider
class NotAcceptableExceptionMapper: BaseExceptionMapper<NotAcceptableException>() {
    override val error: AppErrors = AppErrors.NOT_ACCEPTABLE_MEDIA_TYPE
}
