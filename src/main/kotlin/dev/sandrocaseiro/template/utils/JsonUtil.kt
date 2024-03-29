package dev.sandrocaseiro.template.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.sandrocaseiro.template.serializers.*
import org.jboss.logging.Logger
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val LOGGER: Logger = Logger.getLogger(object{}::class.java.`package`.name)
val MAPPER: ObjectMapper = jacksonObjectMapper().configure()

fun ObjectMapper.configure(): ObjectMapper = this
    .registerModule(SimpleModule()
        .addSerializer(LocalDate::class.java, LocalDateSerializer())
        .addSerializer(LocalTime::class.java, LocalTimeSerializer())
        .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        .addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        .addDeserializer(LocalTime::class.java, LocalTimeDeserializer())
        .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
    ).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

inline fun <reified T> T?.serialize(): String? {
    return if (this == null) null else try {
        MAPPER.writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        LOGGER.error("Json serialization error", e)
        null
    }
}

inline fun <reified T> T?.serializePrettyPrint(): String? {
    return if (this == null) null else try {
        MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this)
    } catch (e: JsonProcessingException) {
        LOGGER.error("Json serialization error", e)
        null
    }
}

inline fun <reified T> InputStream?.deserialize(): T? {
    return if (this == null) null else try {
        MAPPER.readValue<T>(this)
    }
    catch (e: IOException) {
        LOGGER.error("Json deserialization error", e)
        null
    }
}

fun InputStream?.deserializeTree(): JsonNode? {
    return if (this == null) null else try {
        MAPPER.readTree(this)
    }
    catch (e: IOException) {
        LOGGER.error("Json deserialization error", e)
        null
    }
}

inline fun <reified T> JsonNode.deserialize(): T = MAPPER.readValue<T>(this.traverse())
