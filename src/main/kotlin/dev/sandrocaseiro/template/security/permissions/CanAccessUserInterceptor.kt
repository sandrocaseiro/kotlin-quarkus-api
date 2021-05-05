package dev.sandrocaseiro.template.security.permissions

import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.spi.runtime.AuthorizationController
import javax.annotation.Priority
import javax.inject.Inject
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext

@Interceptor
@CanAccessUser("")
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
class CanAccessUserInterceptor {
    @Inject
    lateinit var controller: AuthorizationController
    @Inject
    lateinit var identity: SecurityIdentity

    @AroundInvoke
    fun intercept(ic: InvocationContext): Any {
        if (controller.isAuthorizationEnabled) {
            val annotation = ic.method.getAnnotationsByType(CanAccessUser::class.java).first()
            CanAccessUserCheck(annotation.value).apply(identity, ic.method, ic.parameters)
        }

        return ic.proceed()
    }
}
