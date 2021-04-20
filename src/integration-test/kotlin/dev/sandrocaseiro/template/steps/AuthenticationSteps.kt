package dev.sandrocaseiro.template.steps

import dev.sandrocaseiro.template.StepsState
import dev.sandrocaseiro.template.models.services.SAuth
import dev.sandrocaseiro.template.services.JwtAuthService
import io.cucumber.java.en.Given
import io.quarkus.arc.Unremovable
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
@Unremovable
class AuthenticationSteps {
    @Inject
    lateinit var state: StepsState
    @Inject
    lateinit var jwtAuthService: JwtAuthService

    @Given("I am authenticated")
    fun i_am_authenticated() {
        val user: SAuth = jwtAuthService.authenticate("user1@mail.com", "1234")
        state.token = user.accessToken
    }

    @Given("I am authenticated with {string}")
    fun i_am_authenticated_with(username: String) {
        val user: SAuth = jwtAuthService.authenticate(username, "1234")
        state.token = user.accessToken
    }
}
