//package dev.sandrocaseiro.template.steps
//
//import dev.sandrocaseiro.template.MockServer
//import dev.sandrocaseiro.template.StepsState
//import io.restassured.RestAssured
//import io.restassured.config.EncoderConfig
//import io.restassured.config.LogConfig
//import io.restassured.config.RestAssuredConfig
//import javax.inject.Inject
//
//abstract class BaseSteps {
////    @LocalServerPort
////    var port: Int = 0
//    @Inject
//    lateinit var mockServer: MockServer
//    @Inject
//    lateinit var state: StepsState
////    @Autowired
////    lateinit var flyway: Flyway
//
//    fun setupRestAssured() {
//        RestAssured.config = RestAssuredConfig.config()
//            .encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
//            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().and().enablePrettyPrinting(true))
//        //RestAssured.port = port
//    }
//
//    fun rebuildDbData() {
////        flyway.apply {
////            clean()
////            migrate()
////        }
//    }
//}