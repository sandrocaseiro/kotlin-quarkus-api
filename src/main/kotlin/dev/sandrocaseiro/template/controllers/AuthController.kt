package dev.sandrocaseiro.template.controllers

import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class AuthController {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/v1/token")
    fun token() {

    }
}