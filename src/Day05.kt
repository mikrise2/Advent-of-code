import java.util.*

fun main() {

    fun part1(input: List<String>): String {
        val middleIndex = input.indexOfFirst { it.contains("1") }
        val indexes =
            input[middleIndex].mapIndexed { index, c -> if (c == ' ') -1 else index }.filter { it != -1 }
        val stacks = MutableList(indexes.size) { Stack<Char>() }
        for (i in middleIndex - 1 downTo 0) {
            indexes.forEachIndexed { index, value ->
                if (input[i].length > value) {
                    val element = input[i][value]
                    if (element != ' ')
                        stacks[index].push(element)
                }
            }
        }
        for (i in middleIndex + 2 until input.size) {
            val command = input[i].split(" ")
            repeat(command[1].toInt()) {
                stacks[command[5].toInt() - 1].push(stacks[command[3].toInt() - 1].pop())
            }
        }
        return stacks.map { it.pop() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val middleIndex = input.indexOfFirst { it.contains("1") }
        val indexes =
            input[middleIndex].mapIndexed { index, c -> if (c == ' ') -1 else index }.filter { it != -1 }
        val stacks = MutableList(indexes.size) { Stack<Char>() }
        for (i in middleIndex - 1 downTo 0) {
            indexes.forEachIndexed { index, value ->
                if (input[i].length > value) {
                    val element = input[i][value]
                    if (element != ' ')
                        stacks[index].push(element)
                }
            }
        }
        for (i in middleIndex + 2 until input.size) {
            val command = input[i].split(" ")
            val temp = mutableListOf<Char>()
            repeat(command[1].toInt()) {
                temp.add(stacks[command[3].toInt() - 1].pop())
            }
            temp.reversed().forEach { stacks[command[5].toInt() - 1].push(it) }
        }
        return stacks.map { it.pop() }.joinToString("")
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
