package dev.sandrocaseiro.template.mappers

import dev.sandrocaseiro.template.models.DPage
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery

fun <S : Any, T> PanacheQuery<S>.toDto(map: (S) -> T) = DPage<T>(
    data = this.list().map(map),
    lastPage = !this.hasNextPage(),
    totalPages = this.pageCount(),
    totalItems = this.count(),
    currentPage = this.page().index + 1,
)
