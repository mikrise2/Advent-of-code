fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val ranges = it.split(",")
            val values = ranges.map { range -> range.split("-").map { number -> number.toInt() } }
            if ((values[0][0] >= values[1][0] && values[0][1] <= values[1][1]) || (values[1][0] >= values[0][0] && values[1][1] <= values[0][1]))
                1 as Int
            else
                0 as Int
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val ranges = it.split(",")
            val values = ranges.map { range -> range.split("-").map { number -> number.toInt() } }
            if (values[0][1]>=values[1][0] && values[0][0]<=values[1][1])
                1 as Int
            else
                0 as Int
        }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
