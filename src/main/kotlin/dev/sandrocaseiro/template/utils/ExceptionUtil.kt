package dev.sandrocaseiro.template.utils

import dev.sandrocaseiro.template.exceptions.AppException
import dev.sandrocaseiro.template.localization.AppMessages
import dev.sandrocaseiro.template.models.DResponse
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

fun AppException.toResponse(msg: AppMessages): Response = Response.status(this.error.httpStatus)
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
    .entity(DResponse.notOk(this.error.code, this.error.messageFn(msg)))
    .build()

fun AppException.toResponse(errors: List<DResponse.Error>): Response = Response.status(this.error.httpStatus)
    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
    .entity(DResponse.notOk(errors))
    .build()