package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.Message

@Localized("en")
interface EnglishAppMessages: AppMessages {
    @Message("Server error")
    override fun serverError(): String
}
