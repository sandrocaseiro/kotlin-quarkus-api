package dev.sandrocaseiro.template.filters

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.FilterBadRequestException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.models.dto.DAuthTokenResp
import dev.sandrocaseiro.template.utils.MAPPER
import javax.annotation.Priority
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
@Priority(1)
class ApiFilterResponse: ContainerResponseFilter {
    companion object {
        private const val FILTER_PARAM = "\$filter"
    }

    private val mapper: ObjectMapper = MAPPER

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        val filter = requestContext.uriInfo.queryParameters.getOrDefault(FILTER_PARAM, emptyList())
        if (!responseContext.hasEntity() || filter.isEmpty())
            return

        mapper.addMixIn(Any::class.java, PropertyFilterMixin::class.java)

        val filterProvider = SimpleFilterProvider()
            .addFilter("PropertyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                filter.joinToString { "$it," }.split(",").filter { it.isNotBlank() }.toMutableSet()
            ))
        mapper.setFilterProvider(filterProvider)

        try {
            val body = responseContext.entity
            val json: String = mapper.writeValueAsString(body)

            responseContext.entity = if (Collection::class.java.isAssignableFrom(body!!.javaClass) || body.javaClass.isArray)
                mapper.readValue(json, List::class.java)
            else
                mapper.readValue(json, Map::class.java)
        }
        catch (e: JsonProcessingException) {
            throw FilterBadRequestException(FILTER_PARAM, e)
        }
    }

    @JsonFilter("PropertyFilter")
    object PropertyFilterMixin {}
}
