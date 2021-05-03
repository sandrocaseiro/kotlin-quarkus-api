package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.services.LogService
import io.vertx.core.http.HttpServerRequest
import org.jboss.logging.Logger
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.Context
import javax.ws.rs.ext.Provider

@Provider
class LogRequestFilter: ContainerRequestFilter, ContainerResponseFilter {
    private val logger: Logger = Logger.getLogger(LogRequestFilter::class.java)

    @Context
    lateinit var request: HttpServerRequest

    @Inject
    lateinit var logService: LogService

    override fun filter(requestContext: ContainerRequestContext) {
        logService.build(request)
    }

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        logService.clear()
    }
}
