package dev.sandrocaseiro.template.localization

import io.quarkus.qute.i18n.Localized
import io.quarkus.qute.i18n.Message

@Localized("pt")
interface PortugueseAppMessages: AppMessages {
    @Message("Sucesso")
    override fun success(): String

    @Message("Erro do servidor")
    override fun serverError(): String

    @Message("Requisição inválida")
    override fun badRequest(): String

    @Message("Não autorizado")
    override fun unauthorized(): String

    @Message("Acesso negado")
    override fun forbidden(): String

    @Message("Não encontrado")
    override fun notFound(): String

    @Message("Método não permitido")
    override fun methodNotAllowed(): String

    @Message("Não aceitável")
    override fun notAcceptable(): String

    @Message("Não suportado")
    override fun unsupportedMediaType(): String

    @Message("Token expirado")
    override fun expiredToken(): String

    @Message("Token inválido")
    override fun invalidToken(): String

    @Message("Erro de integração de API. Detalhes: {details}")
    override fun apiError(details: String): String

    @Message("Item não encontrado")
    override fun itemNotFound(): String

    @Message("Nome de usuário já existente")
    override fun usernameAlreadyExists(): String

    @Message("Credenciais inválidas")
    override fun invalidCredentials(): String

    @Message("Erro de validação")
    override fun bindValidation(): String

    @Message("Requisição inválida. Valor inválido para o parâmetro {parameter}")
    override fun pageableRequest(parameter: String): String

    @Message("Requisição inválida. Valor inválido para o parâmetro {parameter}")
    override fun filterRequest(parameter: String): String

    @Message("A propriedade {field} não pode ser vazia")
    override fun validationNotEmpty(field: String): String

    @Message("A propriedade {field} não é um cpf")
    override fun validationCpf(field: String): String

    @Message("A propriedade {field} não pode ser nula")
    override fun validationNotNull(field: String): String

    @Message("O tamanho da propriedade {field} deve ser entre {min} e {max}")
    override fun validationSize(field: String, min: Int, max: Int): String

    @Message("O valor da propriedade {field} deve ser maior ou igual à {value}")
    override fun validationMin(field: String, value: Int): String

    @Message("A propriedade {field} não é um e-mail")
    override fun validationEmail(field: String): String
}
