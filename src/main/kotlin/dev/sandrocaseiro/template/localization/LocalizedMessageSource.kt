package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.MessageBundles
import javax.ws.rs.core.HttpHeaders

class LocalizedMessageSource {
    companion object {
        fun getAppMessages(headers: HttpHeaders): AppMessages {
            val language = headers.acceptableLanguages.firstOrNull()?.language

            return if (language.isNullOrBlank()) MessageBundles.get(AppMessages::class.java)
                else MessageBundles.get(AppMessages::class.java, Localized.Literal.of(language))
        }
    }
}