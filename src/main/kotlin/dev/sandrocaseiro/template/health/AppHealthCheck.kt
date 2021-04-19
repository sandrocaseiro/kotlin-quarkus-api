package dev.sandrocaseiro.template.health

import dev.sandrocaseiro.template.properties.AppProperties
import org.eclipse.microprofile.health.HealthCheck
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness
import javax.enterprise.context.ApplicationScoped

@Liveness
@ApplicationScoped
class AppHealthCheck(
    private val appProperties: AppProperties
): HealthCheck {
    override fun call(): HealthCheckResponse =
        HealthCheckResponse.named("App")
            .up()
            .withData("name", appProperties.name)
            .withData("description", appProperties.description)
            .withData("version", appProperties.version)
            .withData("id", appProperties.id)
            .build()
}
