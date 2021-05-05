package dev.sandrocaseiro.template.exceptions.mappers

import dev.sandrocaseiro.template.exceptions.AppErrors
import javax.ws.rs.NotAllowedException
import javax.ws.rs.ext.Provider

@Provider
class NotAllowedExceptionMapper: BaseExceptionMapper<NotAllowedException>() {
    override val error: AppErrors = AppErrors.NOT_ACCEPTABLE_MEDIA_TYPE
}
