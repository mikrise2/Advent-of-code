fun main() {

    fun isVisible(input: List<String>, element: Char, x: Int, y: Int): Boolean {
        if ((0 until x).all { element.digitToInt() > input[y][it].digitToInt() }) return true
        if ((x + 1..input[y].lastIndex).all { element.digitToInt() > input[y][it].digitToInt() }) return true
        if ((0 until y).all { element.digitToInt() > input[it][x].digitToInt() }) return true
        if ((y + 1..input.lastIndex).all { element.digitToInt() > input[it][x].digitToInt() }) return true
        return false
    }

    fun getView(input: List<String>, element: Char, x: Int, y: Int): Int {
        var result = 1
        var counter = 0
        for (i in x - 1 downTo 0) {
            counter++
            if (element.digitToInt() <= input[i][x].digitToInt())
                break
        }
        result *= counter
        counter = 0
        for (i in x + 1..input[y].lastIndex) {
            counter++
            if (element.digitToInt() <= input[i][x].digitToInt())
                break
        }
        result *= counter
        counter = 0
        for (i in y - 1 downTo 0) {
            counter++
            if (element.digitToInt() <= input[i][x].digitToInt())
                break
        }
        result *= counter
        counter = 0
        for (i in y + 1..input.lastIndex) {
            counter++
            if (element.digitToInt() <= input[i][x].digitToInt())
                break
        }
        result *= counter
        return result
    }

    fun part1(input: List<String>): Int {
        return input.mapIndexed { index, line ->
            line.filterIndexed { cIndex, c ->
                index == 0 || index == input.lastIndex || cIndex == 0 || cIndex == line.lastIndex || isVisible(
                    input, c, cIndex, index
                )
            }
        }.sumOf { it.length }
    }

    fun part2(input: List<String>): Int {
        return input.mapIndexed { index, line ->
            line.mapIndexed { cIndex, c ->
                if (index == 0 || index == input.lastIndex || cIndex == 0 || cIndex == line.lastIndex) 0
                else getView(input, c, cIndex, index)
            }
        }.flatten().max()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
