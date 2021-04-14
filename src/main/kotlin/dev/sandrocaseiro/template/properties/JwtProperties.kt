package dev.sandrocaseiro.template.properties

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
data class JwtProperties(
    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan", defaultValue = "300")
    val expiration: Long,
    @ConfigProperty(name = "jwt.refresh-expiration", defaultValue = "900")
    val refreshExpiration: Long
)
