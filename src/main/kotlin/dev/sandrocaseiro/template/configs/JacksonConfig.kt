package dev.sandrocaseiro.template.configs

import com.fasterxml.jackson.databind.ObjectMapper
import dev.sandrocaseiro.template.utils.configure
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.inject.Singleton

@Singleton
class JacksonConfig : ObjectMapperCustomizer {
    override fun customize(objectMapper: ObjectMapper) {
        objectMapper.configure()
    }
}
