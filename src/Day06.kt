import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf {
            var index = 0
            for (i in 0 until it.length - 4) {
                if (setOf(it[i], it[i + 1], it[i + 2], it[i + 3]).size == 4) {
                    index = i
                    break
                }
            }
            index+4
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            var index = 0
            for (i in 0 until it.length - 4) {
                val set = mutableSetOf<Char>()
                repeat(14){ind->
                    set.add(it[i+ind])
                }
                if (set.size == 14) {
                    index = i
                    break
                }
            }
            index+14
        }
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
