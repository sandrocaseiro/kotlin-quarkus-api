package dev.sandrocaseiro.template.filters

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.models.DResponse
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.ext.Provider

@Provider
class ApiResponseFilter : ContainerResponseFilter {
    @Context
    lateinit var headers: HttpHeaders

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        if (responseContext.hasEntity() && responseContext.entityClass != DResponse::class.java) {
            val messageSource = LocalizedMessageSource.getAppMessages(headers)
            val message = messageSource.success()

            responseContext.entity = DResponse.ok(responseContext.entity, AppErrors.SUCCESS.code, message)
        }
    }
}
