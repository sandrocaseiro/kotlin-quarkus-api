package dev.sandrocaseiro.template.steps

import dev.sandrocaseiro.template.StepsState
import io.cucumber.datatable.DataTable
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.quarkus.arc.Unremovable
import io.quarkus.liquibase.LiquibaseFactory
import io.restassured.RestAssured
import io.restassured.RestAssured.withArgs
import io.restassured.config.EncoderConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.Header
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers.greaterThan
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.MediaType


@ApplicationScoped
@Unremovable
class CommonSteps {
    @Inject
    lateinit var state: StepsState
    @Inject
    lateinit var liquibaseFactory: LiquibaseFactory

    @Before
    fun before() {
        setupRestAssured()
    }

    @After
    fun after() {
        rebuildDbData()
        state.clear()
    }

    @When("I use the header {string} with the value {string}")
    fun i_use_the_header_with_the_value(headerName: String, headerValue: String) {
        state.request
            .header(Header(headerName, headerValue))
    }

    @When("I use form-urlencoded")
    fun i_use_formurlencoded() {
        requestContentType(MediaType.APPLICATION_FORM_URLENCODED)
    }

    @When("I use the Content-Type {string}")
    fun i_use_the_content_type(contentType: String) {
        requestContentType(contentType)
    }

    @When("I use the payload:")
    fun i_use_the_payload(payload: String) {
        state.request
            .body(payload)
        state.setPayload(payload)
    }

    @When("I use the formparams:")
    fun i_use_formparams(table: DataTable) {
        table.asMap<String, String>(String::class.java, String::class.java).forEach { (key, value) ->
            state.request
                .formParam(key, if (value.equals("null", ignoreCase = true)) null else value)
        }
    }

    @When("I use the queryparams:")
    fun i_use_the_queryparams(table: DataTable) {
        table.asMap<String, String>(String::class.java, String::class.java).forEach { (key, value) ->
            state.request
                .queryParam(key, value)
        }
    }

    @When("I DELETE to {string}")
    fun i_delete_to(url: String) {
        state.response = state.request.`when`()
            .delete(url)
    }

    @When("I GET to {string}")
    fun i_get_to_url(url: String) {
        state.response = state.request.`when`()
            .get(url)
    }

    @When("I PATCH to {string}")
    fun i_patch_to(url: String) {
        state.response = state.request.`when`()
            .patch(url)
    }

    @When("I POST to {string}")
    fun i_post_to(url: String) {
        state.response = state.request.`when`()
            .post(url)
    }

    @When("I PUT to {string}")
    fun i_put_to(url: String) {
        state.response = state.request.`when`()
            .put(url)

    }

    @Then("I should receive the status code {int}")
    fun i_should_receive_the_status_code(statusCode: Int) {
        state.response!!.then()
            .assertThat()
            .statusCode(statusCode)
    }

    @Then("The response should have a {string} property")
    fun the_response_should_have_a_property(property: String) {
        state.response!!.then()
            .assertThat()
        //.body("$", hasKeyAtPath(property))
    }

    @Then("The response should not have a {string} property")
    fun the_response_should_not_have_a_property(property: String) {
        state.response!!.then()
            .assertThat()
        //.body("$", not(hasKeyAtPath(property)))
    }

    @Then("The response should have a {string} property with the value {string}")
    fun the_response_should_have_a_property_with_the_value(property: String, value: String) {
        if (value.equals("true", ignoreCase = true) || value.equals("false", ignoreCase = true))
            state.response!!.then()
                .assertThat()
                .body(property, `is`(value.toBoolean()))
        else
            state.response!!.then()
                .assertThat()
                .body(property, `is`(value))
    }

    @Then("The response should have a {string} property containing {string}")
    fun the_response_should_have_a_property_containing(property: String, term: String) {
        state.response!!.then()
            .assertThat()
            .body(property, containsString(term))
    }

    @Then("The response should have a {string} property with the value {int}")
    fun the_response_should_have_a_property_with_the_value(property: String, value: Int) {
        state.response!!.then()
            .assertThat()
            .body(property, `is`(value))
    }

    @Then("The response data should be an empty list")
    fun the_response_data_should_be_an_empty_list() {
        state.response!!.then()
            .assertThat()
            .body("data.size()", `is`(0))
    }

    @Then("The response data should have {int} items")
    fun the_response_data_should_have_items(size: Int) {
        state.response!!.then()
            .assertThat()
            .body("data.size()", `is`(size))
    }

    @Then("The response contains error code {int}")
    fun the_response_contains_error_code(errorCode: Int) {
        state.response!!.then()
            .assertThat()
            .body("errors.code", hasItem(errorCode))
    }

    @Then("The response has {int} errors")
    fun the_response_has_errors(qty: Int) {
        state.response!!.then()
            .assertThat()
            .body("errors.size()", `is`(qty))
    }

    @Then("The response has error containing {string}")
    fun the_response_has_error_containing(field: String) {
        state.response!!.then()
            .assertThat()
            .body("errors.description", hasItem(containsString(field)))

    }

    @Then("The response has {int} errors with code {int}")
    fun the_response_has_errors_with_code(qty: Int, code: Int) {
        state.response!!.then()
            .assertThat()
            .rootPath("errors.findAll { it.code == %s }", withArgs(code))
            .and().body("size()", `is`(qty))
    }

    @Then("The response has {int} errors with code {int} containing {string}")
    fun the_response_has_errors_with_code_containing(qty: Int, code: Int, term: String) {
        state.response!!.then()
            .assertThat()
            .rootPath(
                "errors.findAll { it.code == %s && it.description.contains('%s') }",
                withArgs(code, term)
            )
            .and().body("size()", `is`(qty))
    }

    @Then("The response has an error with code {int} containing {string}")
    fun the_response_has_an_error_with_code_containing(code: Int, term: String) {
        state.response!!.then()
            .assertThat()
            .rootPath(
                "errors.findAll { it.code == %s && it.description.contains('%s') }",
                withArgs(code, term)
            )
            .and().body("size()", greaterThan(0))
    }

    @Then("The response data {word} property should all contains {string}")
    fun the_response_data_property_should_all_contains(field: String, term: String) {
        state.response!!.then()
            .assertThat()
            .rootPath("data.findAll { !it.%s.toLowerCase().contains('%s') }", withArgs(field, term))
            .and().body("size()", equalTo(0))
    }

    @Then("The response data {string} property should have {int} items")
    fun the_response_data_property_should_have_items(field: String, size: Int) {
        state.response!!.then()
            .assertThat()
            .rootPath(field)
            .and().body("size()", `is`(size))
    }

    fun requestContentType(contentType: String) {
        state.request
            .contentType(contentType)
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
        //TODO: Fix Service Locator for Liquibase in CdiObjectFactory
//        liquibaseFactory.createLiquibase().use { liquibase ->
//            liquibase.dropAll()
//            liquibase.update("test")
//        }
    }
}
