package dev.sandrocaseiro.template.models.domain

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ETrace {
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    open var creationDate: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_date")
    open var updateDate: LocalDateTime? = null

    @Column(name = "active", nullable = false)
    open var active: Boolean = true
}
