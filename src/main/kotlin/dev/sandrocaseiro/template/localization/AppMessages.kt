package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Message
import io.quarkus.qute.i18n.MessageBundle

@MessageBundle
interface AppMessages {
    @Message("")
    fun serverError(): String
}
