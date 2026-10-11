package homework.DZ_3.model

data class Employee(
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val position: String,
    val email: String,
    val phone: String,
    val salary: Int?,
    val age: Int
) {
    init {
        require(lastName.isNotBlank()) { "Фамилия не может быть пустой" }
        require(firstName.isNotBlank()) { "Имя не может быть пустым" }
        require(middleName.isNotBlank()) { "Отчество не может быть пустым" }
        require(position.isNotBlank()) { "Должность не может быть пустой" }
        require(email.contains("@")) { "Некорректный email: $email" }
        require(phone.isNotBlank()) { "Телефон не может быть пустым" }
        require(age in 18..100) { "Возраст должен быть в диапазоне 18..100" }
        require(salary != null) { "Зарплата должна быть указана" }
        require(salary > 40) { "Зарплата должна быть больше 40" }
    }

    val fullName: String
        get() = "$lastName $firstName $middleName"

    val isProbation: Boolean
        get() = salary == null

    val formattedSalary: String
        get() = salary?.let { "$it руб." } ?: "испытательный срок"

    fun format(): String = """
        ФИО: $fullName
        Должность: $position
        Email: $email
        Телефон: $phone
        Зарплата: $formattedSalary
        Возраст: $age лет
    """.trimIndent()
}