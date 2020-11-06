package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.domain.ERole
import dev.sandrocaseiro.template.models.domain.EUser
import dev.sandrocaseiro.template.models.dto.DUserCreateReq
import dev.sandrocaseiro.template.models.dto.DUserCreateResp

fun DUserCreateReq.toEUser() = EUser().also {
    it.name = this.name!!
    it.email = this.email!!
    it.cpf = this.cpf!!
    it.password = this.password!!
    it.groupId = this.groupId!!
    it.roles = this.roles!!.map { r -> ERole().apply { id = r } }
}

fun EUser.toCreateResp() = DUserCreateResp(
    id = this.id,
    name = this.name,
    email = this.email,
    groupId = this.groupId,
    roles = this.roles.map { it.id }
)
