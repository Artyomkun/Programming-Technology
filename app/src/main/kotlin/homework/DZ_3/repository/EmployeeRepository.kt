package homework.DZ_3.repository

import homework.DZ_3.model.Employee

interface EmployeeRepository {
    fun findAll(): List<Employee>
    fun findByMinAge(age: Int): List<Employee>
    fun add(employee: Employee)
}