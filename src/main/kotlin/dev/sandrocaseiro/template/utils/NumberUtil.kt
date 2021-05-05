package dev.sandrocaseiro.template.utils

fun String?.toInt(defaultValue: Int): Int {
    return if (this.isNullOrBlank()) defaultValue
        else try {
            this.toInt()
        } catch (e: Exception) {
            defaultValue
        }
}
