package homework.DZ_3.repository

import homework.DZ_3.model.Employee

class InMemoryEmployeeRepository(
    private val employees: MutableList<Employee> = mutableListOf()
) : EmployeeRepository {

    override fun findAll(): List<Employee> = employees.toList()

    override fun findByMinAge(age: Int): List<Employee> =
        employees.filter { it.age > age }

    override fun add(employee: Employee) {
        employees.add(employee)
    }
}