package homework.DZ_3.validation

import homework.DZ_3.model.Employee

object EmployeeValidator {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private val PHONE_REGEX = Regex("^\\+?[0-9\\s\\-()]{10,20}$")

    private const val MIN_AGE = 18
    private const val MAX_AGE = 100
    private const val MIN_SALARY = 1

    fun validate(employee: Employee): ValidationResult {
        val errors = mutableListOf<String>()
        if (employee.lastName.isBlank()) errors += "Фамилия не может быть пустой"
        if (employee.firstName.isBlank()) errors += "Имя не может быть пустым"
        if (employee.middleName.isBlank()) errors += "Отчество не может быть пустым"
        if (employee.position.isBlank()) errors += "Должность не может быть пустой"
        if (!EMAIL_REGEX.matches(employee.email)) { errors += "Некорректный email: ${employee.email}" }
        if (!PHONE_REGEX.matches(employee.phone)) { errors += "Некорректный телефон: ${employee.phone}" }
        if (employee.age !in MIN_AGE..MAX_AGE) { errors += "Возраст должен быть в диапазоне $MIN_AGE..$MAX_AGE, получено: ${employee.age}" }
        employee.salary?.let { salary -> if (salary < MIN_SALARY) { errors += "Зарплата должна быть >= $MIN_SALARY, получено: $salary" } }
        return ValidationResult.of(errors)
    }

    fun isAdult(employee: Employee): Boolean = employee.age >= MIN_AGE
}