package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Message
import io.quarkus.qute.i18n.MessageBundle

@MessageBundle(locale = "en")
interface AppMessages {
    @Message("Success")
    fun success(): String

    @Message("Server error")
    fun serverError(): String

    @Message("Bad request")
    fun badRequest(): String

    @Message("Unauthorized")
    fun unauthorized(): String

    @Message("Access denied")
    fun forbidden(): String

    @Message("Not found")
    fun notFound(): String

    @Message("Method not allowed")
    fun methodNotAllowed(): String

    @Message("Not acceptable")
    fun notAcceptable(): String

    @Message("Unsupported")
    fun unsupportedMediaType(): String

    @Message("Expired token")
    fun expiredToken(): String

    @Message("Invalid token")
    fun invalidToken(): String

    @Message("API Integration Error. Details: {details}")
    fun apiError(details: String): String

    @Message("Item not found")
    fun itemNotFound(): String

    @Message("Username already taken")
    fun usernameAlreadyExists(): String

    @Message("Invalid Credentials")
    fun invalidCredentials(): String

    @Message("Bind validation")
    fun bindValidation(): String

    @Message("Bad request. Invalid value for parameter {parameter}")
    fun pageableRequest(parameter: String): String

    @Message("Bad request. Invalid value for parameter {parameter}")
    fun filterRequest(parameter: String): String

    @Message("The field {field} cannot be empty")
    fun validationNotEmpty(field: String): String

    @Message("The field {field} is not an cpf")
    fun validationCpf(field: String): String

    @Message("The field {field} cannot be null")
    fun validationNotNull(field: String): String

    @Message("The field {field} size must be between {min} and {max}")
    fun validationSize(field: String, min: Int, max: Int): String

    @Message("The field {field} must be greater than or equal to {value}")
    fun validationMin(field: String, value: Int): String

    @Message("The field {field} is not an e-mail")
    fun validationEmail(field: String): String
}
