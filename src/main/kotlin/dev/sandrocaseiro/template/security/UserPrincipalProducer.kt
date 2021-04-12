package dev.sandrocaseiro.template.security

import io.quarkus.security.identity.SecurityIdentity
import org.eclipse.microprofile.jwt.JsonWebToken
import org.jboss.logging.Logger
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Produces

@RequestScoped
class UserPrincipalProducer (
    private val identity: SecurityIdentity
){
    private val logger: Logger = Logger.getLogger(UserPrincipalProducer::class.java)

    @Produces
    @RequestScoped
    fun currentUserPrincipal(): UserPrincipal? {
        logger.info("Principal")
        if (identity.isAnonymous) {
            return null
        }
        if (identity.principal is JsonWebToken) {
            return null
        }

        return UserPrincipal(
            1,
            "sandro",
            "sandro",
            1,
            setOf()
        )


//        throw IllegalStateException("Current principal " + identity.principal + " is not a JSON web token")
    }
}
