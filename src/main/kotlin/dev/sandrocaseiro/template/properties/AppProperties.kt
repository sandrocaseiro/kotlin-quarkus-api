package dev.sandrocaseiro.template.properties

import io.quarkus.arc.config.ConfigProperties

@ConfigProperties(prefix = "app")
class AppProperties {
    lateinit var name: String
    lateinit var description: String
    lateinit var version: String
    lateinit var id: String
}
