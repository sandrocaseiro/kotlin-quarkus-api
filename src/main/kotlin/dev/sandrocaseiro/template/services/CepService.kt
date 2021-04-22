package dev.sandrocaseiro.template.services

import dev.sandrocaseiro.template.models.api.ACep
import javax.enterprise.context.RequestScoped

@RequestScoped
class CepService {
    fun searchCep(cep: String): ACep {
        TODO("Implement this")
    }
}
