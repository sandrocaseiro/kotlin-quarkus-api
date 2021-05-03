package dev.sandrocaseiro.template.utils

import javax.ws.rs.core.Response

fun Response.Status.is4xxClientError(): Boolean = this.statusCode in 400..499

fun Response.Status.is5xxServerError(): Boolean = this.statusCode in 500..599

fun Response.Status.isError(): Boolean = this.is4xxClientError() || this.is5xxServerError()
