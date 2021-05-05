package dev.sandrocaseiro.template.security.permissions

import javax.enterprise.util.Nonbinding
import javax.interceptor.InterceptorBinding

@MustBeDocumented
@InterceptorBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class CanAccessUser(
    @get:Nonbinding val value: String
)
