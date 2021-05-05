package dev.sandrocaseiro.template.security.permissions

import dev.sandrocaseiro.template.security.IAuthenticationInfo
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.spi.runtime.AuthorizationController
import javax.annotation.Priority
import javax.inject.Inject
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext

@Interceptor
@IsGroup(0)
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
class IsGroupInterceptor {
    @Inject
    lateinit var controller: AuthorizationController
    @Inject
    lateinit var identity: SecurityIdentity
    @Inject
    lateinit var authInfo: IAuthenticationInfo

    @AroundInvoke
    fun intercept(ic: InvocationContext): Any {
        if (controller.isAuthorizationEnabled) {
            val annotation = ic.method.getAnnotationsByType(IsGroup::class.java).first()
            IsGroupCheck(annotation.value, authInfo).apply(identity, ic.method, ic.parameters)
        }

        return ic.proceed()
    }
}
