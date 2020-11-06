package dev.sandrocaseiro.template.configs

//import dev.sandrocaseiro.template.handlers.GlobalExceptionHandler
//import dev.sandrocaseiro.template.localization.AppMessages
//import io.quarkus.qute.i18n.MessageBundles
//import io.vertx.core.http.HttpServerRequest
//import java.util.logging.Logger
//import javax.ws.rs.Produces
//import javax.ws.rs.core.Context
//
//class LocalizationConfig {
//    private val LOG: Logger = Logger.getLogger(LocalizationConfig::class.toString())
//    @Context
//    lateinit var request: HttpServerRequest
//
//    @Produces
//    fun bundle(): AppMessages {
//        return MessageBundles.get(AppMessages::class.java)
//    }
//}