package dev.sandrocaseiro.template.clients.factories

import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory
import org.jboss.resteasy.specimpl.MultivaluedMapImpl
import javax.ws.rs.core.MultivaluedMap

class CepApiHeaderFactory(
   @ConfigProperty(name = "apis.cep.apiKey") private val apiKey: String
): ClientHeadersFactory {
    override fun update(
        incomingHeaders: MultivaluedMap<String, String>?,
        clientOutgoingHeaders: MultivaluedMap<String, String>?
    ): MultivaluedMap<String, String> {
        val headers = MultivaluedMapImpl<String, String>()
        headers.add("X-API-KEY", apiKey)

        return headers
    }
}
