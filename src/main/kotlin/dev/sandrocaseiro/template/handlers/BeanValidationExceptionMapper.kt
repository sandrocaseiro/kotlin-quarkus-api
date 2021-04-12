package dev.sandrocaseiro.template.handlers

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.LocalizedMessageSource
import dev.sandrocaseiro.template.models.DResponse
import dev.sandrocaseiro.template.utils.toResponse
import javax.validation.ConstraintViolationException
import javax.validation.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class BeanValidationExceptionMapper: ExceptionMapper<ConstraintViolationException> {
    @Context
    lateinit var headers: HttpHeaders

    override fun toResponse(exception: ConstraintViolationException): Response {
        val messageSource = LocalizedMessageSource.getAppMessages(headers)

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

        return AppException.of(AppErrors.BINDING_VALIDATION_ERROR).toResponse(errors)
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
