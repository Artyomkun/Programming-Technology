package homework.DZ_3.service

import homework.DZ_3.model.Employee
import homework.DZ_3.repository.EmployeeRepository

class EmployeeService(
    private val repository: EmployeeRepository
) {
    fun getAllEmployees(): List<Employee> = repository.findAll()

    fun getEmployeesOlderThan(age: Int): List<Employee> =
        repository.findByMinAge(age)

    fun registerEmployee(employee: Employee) {
        repository.add(employee)
    }
}