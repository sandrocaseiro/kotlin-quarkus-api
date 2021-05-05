package dev.sandrocaseiro.template.exceptions.mappers

import dev.sandrocaseiro.template.exceptions.AppErrors
import javax.ws.rs.NotSupportedException
import javax.ws.rs.ext.Provider

@Provider
class NotSupportedExceptionMapper: BaseExceptionMapper<NotSupportedException>() {
    override val error: AppErrors = AppErrors.UNSUPPORTED_MEDIA_TYPE
}
