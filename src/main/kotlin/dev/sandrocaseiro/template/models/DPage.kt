package dev.sandrocaseiro.template.models

data class DPage<T>(
    val data: List<T> = listOf(),
    val lastPage: Boolean = false,
    val totalPages: Int = 0,
    val totalItems: Long = 0,
    val currentPage: Int = 0
)
