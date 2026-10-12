package homework

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.random.Random

object XorShift32 {
    fun next(state: UInt): UInt {
        var s = state
        s = s xor (s shl 13)
        s = s xor (s shr 17)
        s = s xor (s shl 5)
        return s
    }

    fun prev(state: UInt): UInt {
        var s = state
        s = s xor (s shl 5)
        s = s xor (s shr 17)
        s = s xor (s shl 13)
        return s
    }
}

class Oracle(seed: UInt) {
    private var state: UInt = seed

    fun step() {
        state = XorShift32.next(state)
    }

    fun ask(guess: UInt): Boolean = guess >= state

    fun currentState(): UInt = state

    fun reset(newSeed: UInt) {
        state = newSeed
    }
}

object BinarySearchSolver {
    fun findState(oracle: Oracle): UInt {
        var low = 0u
        var high = UInt.MAX_VALUE
        repeat(32) {
            val mid = low + (high - low) / 2u
            if (oracle.ask(mid)) {
                high = mid
            } else {
                low = mid + 1u
            }
        }
        return low
    }
}

object FlagDecoder {
    private const val KEY1 = 0xDEADBEEFu
    private const val KEY2 = 0xCAFEBABEu
    private const val KEY3 = 0x1337C0DEu

    fun decode(state: UInt): String {
        var s = state
        s = s xor KEY1
        s = s xor (s shl 7)
        s = s xor KEY2
        s = s xor (s shr 11)
        s = s xor KEY3
        return "CTFSPB{${s.toString(16).uppercase()}}"
    }
}

class CtfGame(val seed: UInt) {
    private val oracle = Oracle(seed)
    private var steps = 0
    val maxSteps = 100

    fun step(): Boolean {
        if (steps >= maxSteps) return false
        oracle.step()
        steps++
        return true
    }

    fun ask(guess: UInt): Boolean = oracle.ask(guess)

    fun currentState(): UInt = oracle.currentState()

    fun stepsLeft(): Int = maxSteps - steps

    fun isFinished(): Boolean = steps >= maxSteps

    fun flag(): String = FlagDecoder.decode(oracle.currentState())
}

@Composable
fun DZ2App() {
    var seed by remember { mutableStateOf(Random.nextInt().toUInt()) }
    var game by remember { mutableStateOf(CtfGame(seed)) }
    var guessInput by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("Нажми 'Сделать шаг' или введи догадку") }
    var isError by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("CTFSPB Oracle: xorshift32", style = MaterialTheme.typography.h6)

            Text("Seed: ${seed.toString(16).uppercase()}")
            Text("Шагов сделано: ${game.maxSteps - game.stepsLeft()} / ${game.maxSteps}")

            OutlinedTextField(
                value = guessInput,
                onValueChange = {
                    guessInput = it
                    isError = false
                },
                label = { Text("Догадка (число или hex)") },
                isError = isError,
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    val g = guessInput.trim().toUIntOrNull()
                        ?: guessInput.trim().toUIntOrNull(16)
                    if (g == null) {
                        isError = true
                        output = "Не число. Введи UInt или hex."
                    } else {
                        isError = false
                        output = "Оракул: ${game.ask(g)}"
                    }
                }) {
                    Text("Спросить")
                }

                Button(onClick = {
                    if (game.step()) {
                        output = "Шаг сделан. Осталось: ${game.stepsLeft()}"
                    } else {
                        output = "Игра окончена. Флаг: ${game.flag()}"
                    }
                }) {
                    Text("Сделать шаг")
                }

                Button(onClick = {
                    seed = Random.nextInt().toUInt()
                    game = CtfGame(seed)
                    output = "Новая игра. Seed: ${seed.toString(16).uppercase()}"
                }) {
                    Text("Новая игра")
                }
            }

            Button(onClick = {
                val state = BinarySearchSolver.findState(
                    Oracle(game.currentState())
                )
                output = "Восстановленное состояние: ${state.toString(16).uppercase()}"
            }) {
                Text("Авто-решить (бинарный поиск)")
            }

            if (game.isFinished()) {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Флаг:", style = MaterialTheme.typography.h6)
                        Text(game.flag(), style = MaterialTheme.typography.body1)
                    }
                }
            }

            Text(output, style = MaterialTheme.typography.body2)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "DZ2: CTFSPB Oracle") {
        DZ2App()
    }
}