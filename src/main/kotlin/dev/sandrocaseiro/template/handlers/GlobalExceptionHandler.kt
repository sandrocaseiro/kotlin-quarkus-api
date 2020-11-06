package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.localization.AppMessages
import dev.sandrocaseiro.template.utils.toResponse
import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.MessageBundles
import io.vertx.core.http.HttpServerRequest
import java.util.*
import java.util.logging.Logger
import javax.inject.Inject
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider


@Provider
class GlobalExceptionHandler: ExceptionMapper<Exception> {
    private val LOG: Logger = Logger.getLogger(GlobalExceptionHandler::class.toString())
    @Context
    lateinit var request: HttpServerRequest

    //private val messageSource = MessageBundles.get(AppMessages::class.java)

    override fun toResponse(exception: Exception): Response {
        val locale = Locale.forLanguageTag(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE))
        val messageSource = MessageBundles.get(AppMessages::class.java, Localized.Literal.of(locale.language))
//        return if (BaseException::class.java.isAssignableFrom(exception.javaClass))
//            (exception as AppException).toResponse()
//        else
            return exception.toResponse(messageSource.serverError())
    }
}