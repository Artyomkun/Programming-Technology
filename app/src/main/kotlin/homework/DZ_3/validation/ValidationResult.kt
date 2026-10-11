package homework.DZ_3.validation

sealed class ValidationResult {
    object Valid : ValidationResult()

    data class Invalid(val errors: List<String>) : ValidationResult() {
        init {
            require(errors.isNotEmpty()) { "Список ошибок не может быть пустым" }
        }

        fun format(): String = errors.joinToString("\n") { "  - $it" }
    }

    val isValid: Boolean
        get() = this is Valid

    companion object {
        fun of(errors: List<String>): ValidationResult = if (errors.isEmpty()) Valid else Invalid(errors)
    }
}