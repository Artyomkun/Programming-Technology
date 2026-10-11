//package homework
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Window
//import androidx.compose.ui.window.application
//import kotlin.collections.joinToString
//
//@Composable
//fun App() {
//    val byteVar: Byte = 1
//    val shortVar: Short = 2
//    val intVar: Int = 3
//    val longVar: Long = 4L
//    val floatVar: Float = 5.0f
//    val doubleVar: Double = 6.0
//    val charVar: Char = 'A'
//    val booleanVar: Boolean = true
//    val stringVar: String = "Hello, Kotlin"
//
//    var name by remember { mutableStateOf("") }
//    var number by remember { mutableStateOf("") }
//    var output by remember { mutableStateOf("Введи данные и нажми кнопку") }
//    var isNumberError by remember { mutableStateOf(false) }
//
//    MaterialTheme {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            // 2. Приветствие
//            OutlinedTextField(
//                value = name,
//                onValueChange = { name = it },
//                label = { Text("Твоё имя") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            Button(onClick = {
//                output = if (name.isBlank()) "Ты не ввёл имя!" else greet(name)
//            }) {
//                Text("Поздороваться")
//            }
//
//            OutlinedTextField(
//                value = number,
//                onValueChange = {
//                    number = it
//                    isNumberError = false
//                },
//                label = { Text("Введи целое число") },
//                isError = isNumberError,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Button(onClick = {
//                val numbers = parseNumbers(number)
//                if (numbers == null) {
//                    isNumberError = true
//                    output = "Введи корректное целое число!"
//                } else {
//                    isNumberError = false
//                    output = numbers.joinToString("\n") { n ->
//                        "Число $n: отрицательное? ${isNegative(n)}, ${printSign(n)}"
//                    }
//                }
//            }) {
//                Text("Определить знак")
//            }
//
//            Button(onClick = {
//                output = printMultiplesOfThree()
//            }) {
//                Text("Числа от 1000 до 0, кратные 3")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = output, style = MaterialTheme.typography.body1)
//        }
//    }
//}
//
//fun greet(name: String): String = "Привет, $name!"
//fun isNegative(number: Int): Boolean = number < 0
//
//fun printSign(number: Int): String = when {
//    number < 0 -> "Число $number — отрицательное"
//    number > 0 -> "Число $number — положительное"
//    else -> "Число $number — ноль"
//}
//
//fun printMultiplesOfThree(): String =
//    (1000 downTo 0).filter { it % 3 == 0 }.joinToString(", ")
//
//fun parseNumbers(input: String): List<Int>? {
//    val cleaned = input.trim()
//        .replace(" ", "")
//        .replace(",", "")
//        .replace("\u00A0", "")
//
//    return when {
//        cleaned.startsWith("±") -> {
//            val n = cleaned.removePrefix("±").toIntOrNull() ?: return null
//            listOf(-n, n).distinct()
//        }
//        else -> cleaned.toIntOrNull()?.let { listOf(it) }
//    }
//}
//
//fun main() = application {
//    Window(onCloseRequest = ::exitApplication, title = "ДЗ 1") {
//        App()
//    }
//}