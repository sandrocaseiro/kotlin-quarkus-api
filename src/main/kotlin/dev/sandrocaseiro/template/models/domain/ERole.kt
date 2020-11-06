package dev.sandrocaseiro.template.models.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "Role")
@Table(name = "ROLE")
open class ERole : ETrace() {
    @Id
    @Column(name = "id", nullable = false)
    open var id: Int = 0

    @Column(name = "name", nullable = false, length = 50)
    open var name = ""
}
