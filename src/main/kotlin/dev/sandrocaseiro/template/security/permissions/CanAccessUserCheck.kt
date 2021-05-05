package dev.sandrocaseiro.template.security.permissions

import io.quarkus.security.UnauthorizedException
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.runtime.interceptor.check.SecurityCheck
import java.lang.reflect.Method

class CanAccessUserCheck(
    private val parameterName: String
) : SecurityCheck {
    override fun apply(identity: SecurityIdentity, method: Method, parameters: Array<out Any>) {
        if (identity.isAnonymous)
            throw UnauthorizedException()

        val paramIndex: Int = method.parameters.indexOfFirst { it.name == parameterName }
        if (paramIndex < 0 || parameters.size < paramIndex + 1 || parameters[paramIndex] as Int > 99)
            throw UnauthorizedException()
    }
}
