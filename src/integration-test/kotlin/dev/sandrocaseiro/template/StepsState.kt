package dev.sandrocaseiro.template

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.HttpHeaders

@ApplicationScoped
class StepsState {
    private val REQUEST = "request"
    private val RESPONSE = "response"
    private val TOKEN = "token"
    private val REQUEST_PAYLOAD = "request_payload"

    private val states = mutableMapOf<String, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> get(name: String): T? = states.getOrDefault(name, null) as T?

    fun <T> set(name: String, value: T) {
        states[name] = value
    }

    val request: RequestSpecification
        get() {
            var req: RequestSpecification? = get(REQUEST)
            if (req == null) {
                req = given()
                    .contentType(ContentType.JSON)

                val authToken: String? = token
                if (!authToken.isNullOrEmpty())
                    req.header(HttpHeaders.AUTHORIZATION, "Bearer $authToken")

                set(REQUEST, req)
            }

            return req!!
        }

    var response: Response?
        get() = get(RESPONSE)
        set(value) = set(RESPONSE, value)

    var token: String?
        get() = get(TOKEN)
        set(value) = set(TOKEN, value)

    var requestPayload: JsonNode?
        get() = get(REQUEST_PAYLOAD)
        set(value) = set(REQUEST_PAYLOAD, value)

    fun setPayload(value: String) {
        requestPayload = jacksonObjectMapper().readTree(value)
    }

    fun clear() = states.clear()
}
