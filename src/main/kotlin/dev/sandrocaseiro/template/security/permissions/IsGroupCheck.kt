package dev.sandrocaseiro.template.security.permissions

import dev.sandrocaseiro.template.security.IAuthenticationInfo
import io.quarkus.security.UnauthorizedException
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.runtime.interceptor.check.SecurityCheck
import java.lang.reflect.Method

class IsGroupCheck(
    private val group: Int,
    private val authInfo: IAuthenticationInfo
) : SecurityCheck {
    override fun apply(identity: SecurityIdentity, method: Method, parameters: Array<out Any>) {
        if (identity.isAnonymous || !authInfo.isAuthenticated || authInfo.groupId != group)
            throw UnauthorizedException()
    }
}
