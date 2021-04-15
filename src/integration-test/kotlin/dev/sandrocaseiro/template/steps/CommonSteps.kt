package dev.sandrocaseiro.template.steps

import dev.sandrocaseiro.template.StepsState
import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.quarkus.arc.Unremovable
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.RestAssured.withArgs
import io.restassured.config.EncoderConfig
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.Header
import io.restassured.response.ValidatableResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@ApplicationScoped
@Unremovable
class CommonSteps {
    @Inject
    lateinit var state: StepsState

    private var resp: ValidatableResponse? = null

    @Before
    fun before() {
        setupRestAssured()
    }

    @After
    fun after() {
        rebuildDbData()
        state.clear()
    }

//        When("I use the header {string} with the value {string}") { headerName: String, headerValue: String ->
//            state.request
//                .header(Header(headerName, headerValue))
//        }
//
//        When("I use form-urlencoded") { requestContentType(MediaType.APPLICATION_FORM_URLENCODED) }
//
//        When("I use the Content-Type {string}") { contentType: String -> requestContentType(contentType) }
//
//        When("I use the payload:") { payload: String ->
//            state.request
//                .body(payload)
//            state.setPayload(payload)
//        }
//
//        When("I use the formparams:") { table: DataTable ->
//            table.asMap<String, String>(String::class.java, String::class.java).forEach { (key, value) ->
//                state.request
//                    .formParam(key, if (value.equals("null", ignoreCase = true)) null else value)
//            }
//        }
//
//        When("I use the queryparams:") { table: DataTable ->
//            table.asMap<String, String>(String::class.java, String::class.java).forEach { (key, value) ->
//                state.request
//                    .queryParam(key, value)
//            }
//        }
//
//        When("I DELETE to {string}") { url: String ->
//            state.response = state.request.When {
//                delete(url)
//            }
//        }
//
    @When("I GET to {string}")
    fun i_get_to_url(url: String) {
        state.response = state.request.`when`()
            .get(url)
    }

    //
//        When("I PATCH to {string}") { url: String ->
//            state.response = state.request.When {
//                patch(url)
//            }
//        }
//
//        When("I POST to {string}") { url: String ->
//            state.response = state.request.When {
//                post(url)
//            }
//        }
//
//        When("I PUT to {string}") { url: String ->
//            state.response = state.request.When {
//                put(url)
//            }
//        }
//
    @Then("I should receive the status code {int}")
    fun i_should_receive_the_status_code(statusCode: Int) {
        state.response!!.then()
            .assertThat()
            .statusCode(statusCode)
    }
//
//        Then("The response should have a {string} property") { property: String ->
////            state.response!!.Then {
////                body("$", hasKeyAtPath(property))
////            }
//        }
//
//        Then("The response should not have a {string} property") { property: String ->
////            state.response!!.Then {
////                body("$", CoreMatchers.not(hasKeyAtPath(property)))
////            }
//        }
//
//        Then("The response should have a {string} property with the value {string}") { property: String, value: String ->
//            state.response!!.Then {
//                if (value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true))
//                    body(property, `is`(value.toBoolean()))
//                else
//                    body(property, `is`(value))
//            }
//        }
//
//        Then("The response should have a {string} property containing {string}") { property: String, term: String ->
//            state.response!!.Then {
//                body(property, containsString(term))
//            }
//        }
//
//        Then("The response should have a {string} property with the value {int}") { property: String, value: Int ->
//            state.response!!.Then {
//                body(property, `is`(value))
//            }
//        }
//
//        Then("The response data should be an empty list") {
//            state.response!!.Then {
//                body("data.size()", `is`(0))
//            }
//        }
//
//        Then("The response data should have {int} items") { size: Int ->
//            state.response!!.Then {
//                body("data.size()", `is`(size))
//            }
//        }
//
//        Then("The response contains error code {int}") { errorCode: Int ->
//            state.response!!.Then {
//                body("errors.code", hasItem(errorCode))
//            }
//        }
//
//        Then("The response has {int} errors") { qty: Int ->
//            state.response!!.Then {
//                body("errors.size()", `is`(qty))
//            }
//        }
//
//        Then<String>("The response has error containing {string}") { field: String? ->
//            state.response!!.Then {
//                body("errors.description", hasItem(Matchers.containsString(field)))
//            }
//        }
//
//        Then("The response has {int} errors with code {int}") { qty: Int, code: Int ->
//            state.response!!.Then {
//                rootPath("errors.findAll { it.code == %s }", withArgs(code))
//                body("size()", `is`(qty))
//            }
//        }
//
//        Then("The response has {int} errors with code {int} containing {string}") { qty: Int, code: Int, term: String ->
//            state.response!!.Then {
//                rootPath("errors.findAll { it.code == %s && it.description.contains('%s') }",
//                    withArgs(code, term)
//                )
//                body("size()", `is`(qty))
//            }
//        }
//
//        Then("The response has an error with code {int} containing {string}") { code: Int, term: String ->
//            state.response!!.Then {
//                rootPath("errors.findAll { it.code == %s && it.description.contains('%s') }",
//                    withArgs(code, term)
//                )
//                body("size()", Matchers.greaterThan(0))
//            }
//        }
//
//        Then("The response data {word} property should all contains {string}") { field: String, term: String ->
//            state.response!!.Then {
//                rootPath("data.findAll { !it.%s.toLowerCase().contains('%s') }", withArgs(field, term))
//                body("size()", CoreMatchers.equalTo(0))
//            }
//        }
//
//        Then("The response data {string} property should have {int} items") { field: String, size: Int ->
//            state.response!!.Then {
//                rootPath(field)
//                body("size()", `is`(size))
//            }
//        }

    fun requestContentType(contentType: String) {
//        state.request
//            .contentType(contentType)
    }

    fun setupRestAssured() {
        RestAssured.config = RestAssuredConfig.config()
            .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
//            .logConfig(
//                LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()
//                    //.and().enablePrettyPrinting(true)
//            )
        RestAssured.port = 8081
    }

    fun rebuildDbData() {
//        flyway.apply {
//            clean()
//            migrate()
//        }
    }
}
