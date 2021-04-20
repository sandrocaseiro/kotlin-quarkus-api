package dev.sandrocaseiro.template.models.domain

import javax.persistence.*

@Entity(name = "Role")
@Table(name = "role")
class ERole : ETrace() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0

    @Column(name = "name", nullable = false, length = 50)
    var name = ""
}
