fun change(symbol: Char) =
    if (symbol.isUpperCase()) symbol.lowercase().first().code - 70 else symbol.uppercase().first().code - 64

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val strings = it.chunked(it.length / 2)
            val set = strings[0]
            val element = strings[1].first { symbol -> set.contains(symbol) }
            change(element)
        }
    }

    fun part2(input: List<String>): Int {
        val sets = input.map { it.toSet() }
        var sum = 0
        for (i in input.indices step 3) {
            val element = sets[i].intersect(sets[i + 1].intersect(sets[i + 2])).first()
            sum += change(element)
        }
        return sum
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
