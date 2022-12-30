import java.lang.Exception
import kotlin.math.abs

data class Position(val x: Int, val y: Int, val type: Boolean) {
    val neighbors = mutableListOf<Position?>(null, null, null, null)
    fun neighborByDirection(direction: Int): Position = neighbors[(direction % 4 + 4) % 4]!!
}

fun main() {
    fun part1(inputFirst: List<String>): Int {
        val map = mutableSetOf<Position>()
        val index = inputFirst.indexOfFirst { it.isBlank() }
        val max = inputFirst.subList(0, index).maxOf { it.length }
        val input = inputFirst.mapIndexed { i, s ->
            if (i >= index) s else {
                s.padEnd(max, ' ')
            }
        }
        for (y in 0 until index) {
            for (x in 0..input[y].lastIndex) {
                if (input[y][x] != ' ') {
                    map.add(Position(x, y, input[y][x] != '#'))
                }
            }
        }
        for (point in map) {
            if (point.x > 0 && input[point.y][point.x - 1] != ' ')
                point.neighbors[2] = Position(point.x - 1, point.y, input[point.y][point.x - 1] != '#')
            if (point.x < input[point.y].lastIndex && input[point.y][point.x + 1] != ' ')
                point.neighbors[0] = Position(point.x + 1, point.y, input[point.y][point.x + 1] != '#')
            if (point.y > 0 && input[point.y - 1][point.x] != ' ')
                point.neighbors[3] = Position(point.x, point.y - 1, input[point.y - 1][point.x] != '#')
            if (point.y < index - 1 && input[point.y + 1][point.x] != ' ')
                point.neighbors[1] = Position(point.x, point.y + 1, input[point.y + 1][point.x] != '#')
        }
        for (y in 0 until index) {
            val first = input[y].indexOfFirst { it != ' ' }
            val last = input[y].indexOfLast { it != ' ' }
            map.find { it.y == y && it.x == first }!!.neighbors[2] = Position(last, y, input[y][last] != '#')
            map.find { it.y == y && it.x == last }!!.neighbors[0] = Position(first, y, input[y][first] != '#')
        }

        for (x in 0 until max) {
            val filter = map.filter { it.x == x }
            val first = filter.minOf { it.y }
            val last = filter.maxOf { it.y }
            map.find { it.x == x && it.y == last }!!.neighbors[1] = Position(x, first, input[first][x] != '#')
            map.find { it.x == x && it.y == first }!!.neighbors[3] = Position(x, last, input[last][x] != '#')
        }
        val moves = input[index + 1] + " "
        var direction = 0
        var currentPosition = map.filter { it.y == 0 }.toList().sortedBy { it.x }[0]
        val current = StringBuilder("")
        moves.forEach {
            if (it == 'R' || it == 'L' || it == ' ') {
                val times = current.toString().toInt()
                current.clear()
                repeat(times) {
                    val nextPosition = currentPosition.neighborByDirection(direction)
                    if (nextPosition.type)
                        currentPosition =
                            map.find { position -> position.x == nextPosition.x && position.y == nextPosition.y }!!
                    else
                        return@repeat
                }
                if (it == 'R') direction++
                else if (it == 'L') direction--
            } else {
                current.append(it)
            }
        }
        return 1000 * (currentPosition.y + 1) + 4 * (currentPosition.x + 1) + ((direction % 4 + 4) % 4)
    }

    fun part2(inputFirst: List<String>): Int {
        val map = mutableSetOf<Position>()
        val index = inputFirst.indexOfFirst { it.isBlank() }
        val max = inputFirst.subList(0, index).maxOf { it.length }
        val input = inputFirst.mapIndexed { i, s ->
            if (i >= index) s else {
                s.padEnd(max, ' ')
            }
        }
        for (y in 0 until index) {
            for (x in 0..input[y].lastIndex) {
                if (input[y][x] != ' ') {
                    map.add(Position(x, y, input[y][x] != '#'))
                }
            }
        }
        for (point in map) {
            if (point.x > 0 && input[point.y][point.x - 1] != ' ')
                point.neighbors[2] = Position(point.x - 1, point.y, input[point.y][point.x - 1] != '#')
            if (point.x < input[point.y].lastIndex && input[point.y][point.x + 1] != ' ')
                point.neighbors[0] = Position(point.x + 1, point.y, input[point.y][point.x + 1] != '#')
            if (point.y > 0 && input[point.y - 1][point.x] != ' ')
                point.neighbors[3] = Position(point.x, point.y - 1, input[point.y - 1][point.x] != '#')
            if (point.y < index - 1 && input[point.y + 1][point.x] != ' ')
                point.neighbors[1] = Position(point.x, point.y + 1, input[point.y + 1][point.x] != '#')
        }
//        for (y in 0 until index) {
//            val first = input[y].indexOfFirst { it != ' ' }
//            val last = input[y].indexOfLast { it != ' ' }
//            map.find { it.y == y && it.x == first }!!.neighbors[2] = Position(last, y, input[y][last] != '#')
//            map.find { it.y == y && it.x == last }!!.neighbors[0] = Position(first, y, input[y][first] != '#')
//        }
//
//        for (x in 0 until max) {
//            val filter = map.filter { it.x == x }
//            val first = filter.minOf { it.y }
//            val last = filter.maxOf { it.y }
//            map.find { it.x == x && it.y == last }!!.neighbors[1] = Position(x, first, input[first][x] != '#')
//            map.find { it.x == x && it.y == first }!!.neighbors[3] = Position(x, last, input[last][x] != '#')
//        }
        for (i in 0..49) {
            //1-3
            map.find { it.x == i + 100 && it.y == 49 }!!.neighbors[1] = Position(99, 50 + i, input[50 + i][99] != '#')
            map.find { it.x == 99 && it.y == 50 + i }!!.neighbors[0] = Position(i + 100, 49, input[49][i + 100] != '#')

            //1-4
            map.find { it.x == 149 && it.y == i }!!.neighbors[0] = Position(99, 149 - i, input[149 - i][99] != '#')
            map.find { it.x == 99 && it.y == 149 - i }!!.neighbors[0] = Position(149, i, input[i][149] != '#')

            //3-5
            map.find { it.x == i && it.y == 100 }!!.neighbors[3] = Position(50, 50 + i, input[50 + i][50] != '#')
            map.find { it.x == 50 && it.y == 50 + i }!!.neighbors[2] = Position(i, 100, input[100][i] != '#')

            //2-5
            map.find { it.x == 0 && it.y == 100 + i }!!.neighbors[2] = Position(50, 49 - i, input[49 - i][50] != '#')
            map.find { it.x == 50 && it.y == 49 - i }!!.neighbors[2] = Position(0, 100 + i, input[100 + i][0] != '#')

            // 1-6
            map.find { it.x == 100 + i && it.y == 0 }!!.neighbors[3] = Position(i, 199, input[199][i] != '#')
            map.find { it.x == i && it.y == 199 }!!.neighbors[1] = Position(100 + i, 0, input[0][100 + i] != '#')

            //2-6
            map.find { it.x == 50 + i && it.y == 0 }!!.neighbors[3] = Position(0, 150 + i, input[150 + i][0] != '#')
            map.find { it.x == 0 && it.y == 150 + i }!!.neighbors[2] = Position(50 + i, 0, input[0][50 + i] != '#')

            //4-6
            map.find { it.x == 49 && it.y == 150 + i }!!.neighbors[0] =
                Position(50 + i, 149, input[149][50 + i] != '#')
            map.find { it.x == 50 + i && it.y == 149 }!!.neighbors[1] = Position(49, 150 + i, input[150 + i][49] != '#')
        }
        val moves = input[index + 1] + " "
        var direction = 0
        var currentPosition = map.filter { it.y == 0 }.toList().sortedBy { it.x }[0]
        val current = StringBuilder("")
        moves.forEach {
            if (it == 'R' || it == 'L' || it == ' ') {
                val times = current.toString().toInt()
                current.clear()
                repeat(times) {
                    val nextPosition = currentPosition.neighborByDirection(direction)
                    if (nextPosition.type)
                        currentPosition =
                            map.find { position -> position.x == nextPosition.x && position.y == nextPosition.y }!!
                    else
                        return@repeat
                }
                if (it == 'R') direction++
                else if (it == 'L') direction--
            } else {
                current.append(it)
            }
        }
        return 1000 * (currentPosition.y + 1) + 4 * (currentPosition.x + 1) + ((direction % 4 + 4) % 4)
    }
    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}
