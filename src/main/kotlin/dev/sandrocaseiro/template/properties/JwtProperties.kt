package dev.sandrocaseiro.template.properties

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class JwtProperties {
    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan", defaultValue = "300")
    var expiration: Long = 0

    @ConfigProperty(name = "jwt. refresh-expiration", defaultValue = "300")
    var refreshExpiration: Long = 0
}
