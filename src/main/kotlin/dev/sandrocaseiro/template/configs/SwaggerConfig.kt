package dev.sandrocaseiro.template.configs

import dev.sandrocaseiro.template.exceptions.AppErrors
import org.eclipse.microprofile.openapi.OASFactory
import org.eclipse.microprofile.openapi.OASFilter
import org.eclipse.microprofile.openapi.OASModelReader
import org.eclipse.microprofile.openapi.models.OpenAPI
import org.eclipse.microprofile.openapi.models.media.Schema
import org.jboss.logging.Logger

class SwaggerConfig: OASModelReader, OASFilter {
//class SwaggerConfig: OASFilter {
    val logger: Logger = Logger.getLogger(SwaggerConfig::class.java)

    override fun buildModel(): OpenAPI {
        return OASFactory.createObject(OpenAPI::class.java)
            .info(OASFactory.createInfo()
                .title("api-template")
                .version("1.0.0")
                .description("Kotlin Quarkus Template API")
            )
    }

    override fun filterSchema(schema: Schema): Schema {
        //logger.info(schema)

        return schema;
    }

//    override fun filterOpenAPI(openAPI: OpenAPI) {
//       logger.info("Open API")
//
//        val errorSchema = openAPI.components.schemas["Error"]!!
//        val codeSchema = errorSchema.properties["code"]!!
//
//        val builder = StringBuilder(codeSchema.description ?: "")
//            .append(System.lineSeparator()).append(System.lineSeparator())
//            .append(System.lineSeparator()).append(System.lineSeparator())
//
//        AppErrors.values().forEach {
//            //codeSchema.addEnumItemObject(it.code)
//            builder
//                .append("* ")
//                .append(it.code)
//                .append(" - ")
//                .append(it.toString())
//                .append(System.lineSeparator()).append(System.lineSeparator())
//        }
//        codeSchema.description(builder.toString())
//    }
}