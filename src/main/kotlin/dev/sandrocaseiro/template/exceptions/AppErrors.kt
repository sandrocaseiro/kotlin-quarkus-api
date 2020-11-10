package dev.sandrocaseiro.template.exceptions

import dev.sandrocaseiro.template.localization.AppMessages
import javax.ws.rs.core.Response.Status

enum class AppErrors(
    val httpStatus: Status,
    val code: Int,
    val messageFn: (AppMessages) -> String
) {

    SUCCESS(Status.OK, 200, { msg -> msg.success() }) {
        override fun throws() = throw AppException(this)
    },
    SERVER_ERROR(Status.INTERNAL_SERVER_ERROR, 500, { it.serverError() }) {
        override fun throws() = throw AppException(this)
    },
    BAD_REQUEST_ERROR(Status.BAD_REQUEST, 400, { it.badRequest() }) {
        override fun throws() = throw AppException(this)
    },
    UNAUTHORIZED_ERROR(Status.UNAUTHORIZED, 401, { it.unauthorized() }) {
        override fun throws() = throw AppException(this)
    },
    FORBIDDEN_ERROR(Status.FORBIDDEN, 403, { it.forbidden() }) {
        override fun throws() = throw AppException(this)
    },
    NOT_FOUND_ERROR(Status.NOT_FOUND, 404, { it.notFound() }) {
        override fun throws() = throw AppException(this)
    },
    METHOD_NOT_ALLOWED_ERROR(Status.METHOD_NOT_ALLOWED, 405, { it.methodNotAllowed() }) {
        override fun throws() = throw AppException(this)
    },
    NOT_ACCEPTABLE_MEDIA_TYPE(Status.NOT_ACCEPTABLE, 406, { it.notAcceptable() }) {
        override fun throws() = throw AppException(this)
    },
    UNSUPPORTED_MEDIA_TYPE(Status.UNSUPPORTED_MEDIA_TYPE, 415, { it.unsupportedMediaType() }) {
        override fun throws() = throw AppException(this)
    },
    TOKEN_EXPIRED_ERROR(Status.UNAUTHORIZED, 900, { it.expiredToken() }) {
        override fun throws() = throw AppException(this)
    },
    INVALID_TOKEN_ERROR(Status.UNAUTHORIZED, 901, { it.invalidToken() }) {
        override fun throws() = throw AppException(this)
    },
    API_ERROR(Status.INTERNAL_SERVER_ERROR, 902, { it.apiError("") }) {
        override fun throws() = throw AppException(this)
    },
    ITEM_NOT_FOUND_ERROR(Status.NOT_FOUND, 903, { it.itemNotFound() }) {
        override fun throws() = throw AppException(this)
    },
    USERNAME_ALREADY_EXISTS(Status.BAD_REQUEST, 904, { it.usernameAlreadyExists() }) {
        override fun throws() = throw AppException(this)
    },
    BINDING_VALIDATION_ERROR(Status.BAD_REQUEST, 905, { it.bindValidation() }) {
        override fun throws() = throw AppException(this)
    },
    PAGEABLE_REQUEST_ERROR(Status.BAD_REQUEST, 906, { it.pageableRequest("") }) {
        override fun throws() = throw AppException(this)
    },
    FILTER_REQUEST_ERROR(Status.BAD_REQUEST, 907, { it.filterRequest("") }) {
        override fun throws() = throw AppException(this)
    },
    INVALID_CREDENTIALS(Status.BAD_REQUEST, 909, { it.invalidCredentials() }) {
        override fun throws() = throw AppException(this)
    }
    ;

    abstract fun throws(): Nothing
}