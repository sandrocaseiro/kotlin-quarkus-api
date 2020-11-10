package dev.sandrocaseiro.template.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotEmptyValidator : ConstraintValidator<NotEmpty, Any?> {
    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        return when (value) {
            null -> false
            is List<*> -> value.isNotEmpty()
            is Map<*, *> -> value.isNotEmpty()
            is String -> !(value as String?).isNullOrEmpty()
            else -> true
        }
    }
}
