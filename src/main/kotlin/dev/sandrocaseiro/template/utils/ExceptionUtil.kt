package dev.sandrocaseiro.template.utils

import dev.sandrocaseiro.template.exceptions.AppErrors
import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.models.DResponse
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

fun Exception.toResponse(message: String): Response = Response.status(AppErrors.SERVER_ERROR.httpStatus)
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
    .entity(DResponse.notOk(AppErrors.SERVER_ERROR.code, message))
    .build()

fun AppException.toResponse(): Response = Response.status(this.error.httpStatus)
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
    .entity(DResponse.notOk(this.error.code, this.error.messageRes))
    .build()