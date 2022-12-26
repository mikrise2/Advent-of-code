fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var result = 0
        var tact = 1
        val commands = input.map {
            if (it == "noop")
                Pair(1, 0)
            else
                Pair(2, it.split(" ")[1].toInt())
        }.toMutableList()
        var cursor = 0
        while (tact <= 220 && cursor != input.size) {
            if (tact % 40 == 20) {
                result += tact * x
            }
            commands[cursor] = Pair(commands[cursor].first - 1, commands[cursor].second)
            if (commands[cursor].first == 0) {
                x += commands[cursor].second
                cursor++
            }
            tact++
        }
        return result
    }

    fun part2(input: List<String>): String {
        var x = 1
        var tact = 0
        val commands = input.map {
            if (it == "noop")
                Pair(1, 0)
            else
                Pair(2, it.split(" ")[1].toInt())
        }.toMutableList()
        var cursor = 0
        val line = StringBuilder("")
        val lines = mutableListOf<String>()
        while (tact <= 240) {
            val index = (tact % 40)
            if (index == 0) {
                lines.add(line.toString())
                line.clear()
            }
            line.append(
                if (index >= x - 1 && index <= x + 1) {
                    "#"
                } else "."
            )
            if (cursor != input.size) {
                commands[cursor] = Pair(commands[cursor].first - 1, commands[cursor].second)
                if (commands[cursor].first == 0) {
                    x += commands[cursor].second
                    cursor++
                }
            }
            tact++
        }
        lines.forEach {
            println(it)
        }
        return ""
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
