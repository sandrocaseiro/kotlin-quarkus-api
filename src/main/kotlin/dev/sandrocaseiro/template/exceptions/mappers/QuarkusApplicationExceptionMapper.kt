package dev.sandrocaseiro.template.exceptions.mappers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.utils.toResponse
import io.quarkus.arc.ArcUndeclaredThrowableException
import io.quarkus.security.AuthenticationFailedException
import io.quarkus.security.UnauthorizedException
import io.smallrye.jwt.auth.principal.ParseException
import org.jboss.logging.Logger
import org.jboss.resteasy.spi.ApplicationException
import org.jose4j.jwt.consumer.InvalidJwtException
import javax.validation.ConstraintViolationException
import javax.validation.Path
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
class QuarkusApplicationExceptionMapper: BaseExceptionMapper<ApplicationException>() {
    override val logger: Logger = Logger.getLogger(QuarkusApplicationExceptionMapper::class.java)

    override fun toResponse(exception: ApplicationException): Response {
        logger.error("Exception Handler", exception)

        return when (exception.cause) {
            is UnauthorizedException -> toResponse(AppErrors.UNAUTHORIZED_ERROR)
            is NotFoundException -> toResponse(AppErrors.NOT_FOUND_ERROR)
            is AuthenticationFailedException -> toResponse(AppErrors.INVALID_TOKEN_ERROR)
            is ArcUndeclaredThrowableException -> treatArcException(exception.cause as ArcUndeclaredThrowableException)
            is ConstraintViolationException -> treatConstraintValidationException(exception.cause as ConstraintViolationException)
            is AppException -> (exception.cause as AppException).toResponse(messageSource)
            else -> toResponse(AppErrors.SERVER_ERROR)
        }
    }

    private fun treatArcException(exception: ArcUndeclaredThrowableException): Response =
        when (exception.cause) {
            is ParseException -> treatJwtException(exception.cause as ParseException)
            else -> toResponse(AppErrors.SERVER_ERROR)
        }

    private fun treatJwtException(exception: ParseException): Response =
        when (exception.cause) {
            is InvalidJwtException -> toResponse(AppErrors.INVALID_TOKEN_ERROR)
            else -> toResponse(AppErrors.SERVER_ERROR)
        }

    private fun treatConstraintValidationException(exception: ConstraintViolationException): Response {
        val errors = mutableListOf<DResponse.Error>()
        for (error in exception.constraintViolations) {
            val field = lastFieldName(error.propertyPath.iterator())
            val message: String = when (error.messageTemplate) {
                "{validation.notempty}" -> messageSource.validationNotEmpty(field)
                "{validation.cpf}" -> messageSource.validationCpf(field)
                "{javax.validation.constraints.NotNull.message}" -> messageSource.validationNotNull(field)
                "{javax.validation.constraints.Size.message}" ->
                    messageSource.validationSize(field, validatorAttribute(error.constraintDescriptor.attributes, "min"),
                        validatorAttribute(error.constraintDescriptor.attributes, "max"))
                "{javax.validation.constraints.Min.message}" ->
                    messageSource.validationMin(field, validatorAttribute(error.constraintDescriptor.attributes, "value"))
                "{javax.validation.constraints.Email.message}" -> messageSource.validationEmail(field)
                else -> error.message
            }

            errors.add(DResponse.Error.error(AppErrors.BINDING_VALIDATION_ERROR.code, message))
        }

        return toResponse(AppErrors.BINDING_VALIDATION_ERROR, errors)
    }

    private fun lastFieldName(nodes: Iterator<Path.Node>): String {
        var last: Path.Node? = null
        while (nodes.hasNext())
            last = nodes.next()

        return last?.name ?: nodes.toString()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> validatorAttribute(attributes: Map<String, Any>, name: String): T = attributes[name] as T
}
