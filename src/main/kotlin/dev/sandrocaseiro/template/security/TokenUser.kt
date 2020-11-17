package dev.sandrocaseiro.template.security

import io.quarkus.security.User

class TokenUser(
    val id: Int,
    val name: String,
    val email: String,
    password: String,
    val groupId: Int,
    val roles: List<Int>
)// : User(email, password, emptyList())