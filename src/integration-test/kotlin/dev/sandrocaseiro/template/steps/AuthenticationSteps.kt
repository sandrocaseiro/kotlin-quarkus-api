//package dev.sandrocaseiro.template.steps
//
//import dev.sandrocaseiro.template.services.JwtAuthService
//import io.quarkus.arc.Unremovable
//import javax.enterprise.context.ApplicationScoped
//import javax.inject.Inject
//
//@ApplicationScoped
//@Unremovable
//class AuthenticationSteps {
////    @Inject
////    lateinit var jwtAuthService: JwtAuthService
//
////    init {
////        Given("I am authenticated") {
//////            val user: TokenUser = jwtAuthService.loadUserByUsername("user1@mail.com") as TokenUser
//////            val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)
//////            state.token = tokenResp.accessToken
////        }
////
////        Given("I am authenticated with {string}") { username: String ->
//////            val user: TokenUser = jwtAuthService.loadUserByUsername(username) as TokenUser
//////            val tokenResp: TokenAuthResponse = jwtAuthService.generateTokenResponse(user)
//////            state.token = tokenResp.accessToken
////        }
////    }
//}
