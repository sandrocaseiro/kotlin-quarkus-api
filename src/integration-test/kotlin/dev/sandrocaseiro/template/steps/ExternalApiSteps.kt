package dev.sandrocaseiro.template.steps

import com.github.tomakehurst.wiremock.client.WireMock
import dev.sandrocaseiro.template.MockServer
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.quarkus.arc.Unremovable
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

@ApplicationScoped
@Unremovable
class ExternalApiSteps {
    @Inject
    lateinit var mockServer: MockServer

    @Before
    fun before_api() {
        mockServer.reset()
    }

    @Given("CEP API is not working")
    fun cep_api_is_not_working() {
        stubNotWorking()
    }

    @Given("CEP API is working")
    fun cep_api_is_working() {
        stubIsWorking()
    }

    private fun stubNotWorking() {
        WireMock.stubFor(
            WireMock.any(WireMock.urlPathMatching("/cep/.*"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(Response.Status.INTERNAL_SERVER_ERROR.statusCode)
                        .withFixedDelay(5000)
                )
        )
    }

    companion object {
        fun stubIsWorking() {
            WireMock.stubFor(
                WireMock.get(WireMock.urlPathMatching("/cep/03310000/.*"))
                    .atPriority(1)
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(Response.Status.OK.statusCode)
                            .withBodyFile("/cep.json")
                    )
            )


            WireMock.stubFor(
                WireMock.get(WireMock.urlPathMatching("/cep/99999999/.*"))
                    .atPriority(1)
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(Response.Status.OK.statusCode)
                            .withBodyFile("/cep-not-found.json")
                    )
            )

            WireMock.stubFor(
                WireMock.any(WireMock.urlPathMatching("/cep/.*"))
                    .atPriority(99)
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(Response.Status.NOT_FOUND.statusCode)
                    )
            )
        }
    }
}
