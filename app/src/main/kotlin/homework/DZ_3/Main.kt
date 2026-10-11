package homework.DZ_3.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import homework.DZ_3.model.Employee
import homework.DZ_3.repository.InMemoryEmployeeRepository
import homework.DZ_3.service.EmployeeService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = InMemoryEmployeeRepository()
        val service = EmployeeService(repository)

        val employees = listOf(
            Employee("Петров", "Алексей", "Иванович", "Менеджер", "petrov@example.ru", "+7 (999) 123-45-67", 60000, 45),
            Employee("Сидоров", "Иван", "Петрович", "Разработчик", "sidorov@example.ru", "+7 (999) 234-56-78", 80000, 50),
            Employee("Кузнецова", "Анна", "Сергеевна", "Аналитик", "kuznetsova@example.ru", "+7 (999) 345-67-89", 70000, 41),
            Employee("Смирнов", "Дмитрий", "Алексеевич", "Тестировщик", "smirnov@example.ru", "+7 (999) 456-78-90", 50000, 45),
            Employee("Волкова", "Екатерина", "Дмитриевна", "Дизайнер", "volkova@example.ru", "+7 (999) 567-89-01", 70000, 47)
        )

        employees.forEach { service.registerEmployee(it) }

        val all = service.getAllEmployees()

        setContent {
            MaterialTheme {
                EmployeeList(all)
            }
        }
    }
}

@Composable
private fun EmployeeList(employees: List<Employee>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(employees) { e ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = e.fullName, style = MaterialTheme.typography.titleMedium)
                Text(text = "Должность: ${e.position}")
                Text(text = "Email: ${e.email}")
                Text(text = "Телефон: ${e.phone}")
                Text(text = "Зарплата: ${e.formattedSalary}")
                Text(text = "Возраст: ${e.age}")
            }
        }
    }
}