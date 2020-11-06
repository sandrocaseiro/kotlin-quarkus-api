package dev.sandrocaseiro.template.exceptions

import javax.ws.rs.core.Response.Status

enum class AppErrors(
    val httpStatus: Status,
    val code: Int,
    val messageRes: String
) {

    SUCCESS(Status.OK, 200, "error.success") {
        override fun throws() = throw AppException(this)
    },
    SERVER_ERROR(Status.INTERNAL_SERVER_ERROR,500, "error.server") {
        override fun throws() = throw AppException(this)
    },
    BAD_REQUEST_ERROR(Status.BAD_REQUEST, 400, "error.badrequest") {
        override fun throws() = throw AppException(this)
    },
    UNAUTHORIZED_ERROR(Status.UNAUTHORIZED,401, "error.unauthorized") {
        override fun throws() = throw AppException(this)
    },
    FORBIDDEN_ERROR(Status.FORBIDDEN,403, "error.forbidden") {
        override fun throws() = throw AppException(this)
    },
    NOT_FOUND_ERROR(Status.NOT_FOUND,404, "error.notfound") {
        override fun throws() = throw AppException(this)
    },
    METHOD_NOT_ALLOWED_ERROR(Status.METHOD_NOT_ALLOWED, 405, "error.methodnotallowed") {
        override fun throws() = throw AppException(this)
    },
    NOT_ACCEPTABLE_MEDIA_TYPE(Status.NOT_ACCEPTABLE, 406, "error.notacceptable") {
        override fun throws() = throw AppException(this)
    },
    UNSUPPORTED_MEDIA_TYPE(Status.UNSUPPORTED_MEDIA_TYPE, 415, "error.unsupportedmediatype") {
        override fun throws() = throw AppException(this)
    },
    TOKEN_EXPIRED_ERROR(Status.UNAUTHORIZED,900, "error.tokenexpired") {
        override fun throws() = throw AppException(this)
    },
    INVALID_TOKEN_ERROR(Status.UNAUTHORIZED, 901, "error.invalidtoken") {
        override fun throws() = throw AppException(this)
    },
    API_ERROR(Status.INTERNAL_SERVER_ERROR,902, "error.api") {
        override fun throws() = throw AppException(this)
    },
    ITEM_NOT_FOUND_ERROR(Status.NOT_FOUND, 903, "error.itemnotfound") {
        override fun throws() = throw AppException(this)
    },
    USERNAME_ALREADY_EXISTS(Status.BAD_REQUEST,904, "error.usernamealreadyexists") {
        override fun throws() = throw AppException(this)
    },
//    BINDING_VALIDATION_ERROR(Status.UNPROCESSABLE_ENTITY, 905, "error.bindvalidation") {
//        override fun throws() = throw AppException(this)
//    },
    BINDING_VALIDATION_ERROR(Status.BAD_REQUEST, 905, "error.bindvalidation") {
        override fun throws() = throw AppException(this)
    },
    PAGEABLE_REQUEST_ERROR(Status.BAD_REQUEST, 906, "error.pageablerequest") {
        override fun throws() = throw AppException(this)
    },
    FILTER_REQUEST_ERROR(Status.BAD_REQUEST, 907, "error.filterrequest") {
        override fun throws() = throw AppException(this)
    },
    INVALID_CREDENTIALS(Status.BAD_REQUEST, 909, "error.invalidcredentials") {
        override fun throws() = throw AppException(this)
    },
    ;

    abstract fun throws(): Nothing
}