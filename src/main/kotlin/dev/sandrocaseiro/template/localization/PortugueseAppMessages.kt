package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.Message

@Localized("pt")
interface PortugueseAppMessages: AppMessages {
    @Message("Erro do servidor")
    override fun serverError(): String
}
