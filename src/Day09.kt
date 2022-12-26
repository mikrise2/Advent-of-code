import kotlin.math.abs

fun main() {
    fun headsCount(input: List<String>, size: Int): Int {
        val x = MutableList(size) { 0 }
        val y = MutableList(size) { 0 }
        val tails = mutableSetOf(Pair(0, 0))
        input.forEach { line ->
            val command = line.split(" ")
            repeat(command[1].toInt()) {
                when (command[0]) {
                    "R" -> x[0]++
                    "L" -> x[0]--
                    "U" -> y[0]++
                    "D" -> y[0]--
                }
                for (i in 1 until size) {
                    val differentX = x[i - 1] - x[i]
                    val differentY = y[i - 1] - y[i]
                    if (abs(differentX) >= 2 || abs(differentY) >= 2) {
                        x[i] = x[i] + differentX.coerceIn(-1..1)
                        y[i] = y[i] + differentY.coerceIn(-1..1)
                    }
                }
                tails.add(Pair(x[size - 1], y[size - 1]))
            }
        }
        return tails.size
    }

    fun part1(input: List<String>): Int {
        return headsCount(input, 2)
    }

    fun part2(input: List<String>): Int {
        return headsCount(input, 10)
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
