import java.util.LinkedList
import java.util.Queue
import kotlin.math.floor

fun main() {
    data class Monkey(
        val items: Queue<Long>,
        val operation: (Long) -> Long,
        val divisibleBy: Long,
        val trueMonkey: Int,
        val falseMonkey: Int,
    )

    fun progress(input: List<String>, div: Boolean, repeats: Int): Long {
        val monkeys = input.filter { it.isNotEmpty() }.chunked(6).map {
            val items = it[1].trim().split(" ").filterIndexed { index, _ -> index > 1 }.map { number ->
                if (number.contains(",")) number.replace(",", "").toLong()
                else number.toLong()
            }.toMutableList()
            val operation = { numberInt: Long ->
                val split = it[2].trim().split(" ")
                when (split[4]) {
                    "*" -> {
                        if (split[5] != "old")
                            numberInt * split[5].toLong()
                        else {
                            numberInt * numberInt
                        }
                    }

                    else -> {
                        if (split[5] != "old")
                            numberInt + split[5].toLong()
                        else {
                            numberInt + numberInt
                        }
                    }
                }
            }
            val dividedBy = it[3].trim().split(" ")[3].toLong()
            val trueMonkey = it[4].trim().split(" ")[5].toInt()
            val falseMonkey = it[5].trim().split(" ")[5].toInt()
            Monkey(LinkedList(items), operation, dividedBy, trueMonkey, falseMonkey)
        }
        val divisor = monkeys.fold(1L) { acc, monkey -> acc * monkey.divisibleBy }
        val monkeyInspects = MutableList(monkeys.size) { 0L }
        repeat(repeats) {
            monkeys.forEachIndexed { index, monkey ->
                while (monkey.items.isNotEmpty()) {
                    monkeyInspects[index]++
                    val withOperation = monkey.operation(monkey.items.poll())
                    val element = if(div) (withOperation.toFloat() / 3).toLong() else
                        (withOperation % divisor)
                    if (element % monkey.divisibleBy == 0L) {
                        monkeys[monkey.trueMonkey].items.add(element)
                    } else {
                        monkeys[monkey.falseMonkey].items.add(element)
                    }
                }
            }
        }
        return monkeyInspects.sortedDescending().take(2).fold(1L) { acc, number -> acc * number }
    }

    fun part1(input: List<String>): Long {
        return progress(input, true, 20)
    }

    fun part2(input: List<String>): Long {
        return progress(input, false, 10000)
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
