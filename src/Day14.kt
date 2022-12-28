import java.lang.IndexOutOfBoundsException

fun main() {

    fun createWalls(
        walls: List<List<Pair<Int, Int>>>,
        height: Int,
        width: Int,
        mapStartX: Int
    ): List<MutableList<Int>> {
        val map = List(height) { MutableList(width) { 0 } }
        walls.forEach { wall ->
            var current = wall[0]
            for (i in 1 until wall.size) {
                if (wall[i].first == current.first) {
                    if (wall[i].second > current.second) {
                        for (j in current.second..wall[i].second) {
                            map[j][current.first - mapStartX] = 1
                        }
                    } else {
                        for (j in current.second downTo wall[i].second) {
                            map[j][current.first - mapStartX] = 1
                        }
                    }
                } else {
                    if (wall[i].first > current.first) {
                        for (j in (current.first - mapStartX)..(wall[i].first - mapStartX)) {
                            map[current.second][j] = 1
                        }
                    } else {
                        for (j in (current.first - mapStartX) downTo (wall[i].first - mapStartX)) {
                            map[current.second][j] = 1
                        }
                    }
                }
                current = wall[i]
            }
        }
        return map
    }

    fun part1(input: List<String>): Int {
        val walls = input.map {
            it.split("->").map { coordinates ->
                val split = coordinates.split(",").map { coordinate -> coordinate.trim().toInt() }
                Pair(split[0], split[1])
            }
        }
        val mapStartX = walls.minOf { it.minOf { coordinate -> coordinate.first } }
        val mapFinishX = walls.maxOf { it.maxOf { coordinate -> coordinate.first } }
        val mapStartY = 0
        val mapFinishY = walls.maxOf { it.maxOf { coordinate -> coordinate.second } }
        val width = mapFinishX - mapStartX + 1
        val height = mapFinishY - mapStartY + 1
        val map = createWalls(walls, height, width, mapStartX)
        var counter = 0
        var checker = true
        inner@ while (checker) {
            counter++
            var position = Pair(500 - mapStartX, 0)
            while (position.first in 0..mapFinishX - mapStartX && position.second <= mapFinishY) {
                try {
                    if (map[position.second + 1][position.first] == 0) {
                        position = Pair(position.first, position.second + 1)
                    } else if (map[position.second + 1][position.first - 1] == 0) {
                        position = Pair(position.first - 1, position.second + 1)
                    } else if (map[position.second + 1][position.first + 1] == 0) {
                        position = Pair(position.first + 1, position.second + 1)
                    } else {
                        map[position.second][position.first] = 2
                        continue@inner
                    }
                } catch (_: IndexOutOfBoundsException) {
                    checker = false
                    continue@inner
                }
            }
            break
        }
        return counter - 1
    }

    fun part2(input: List<String>): Int {
        val walls = input.map {
            it.split("->").map { coordinates ->
                val split = coordinates.split(",").map { coordinate -> coordinate.trim().toInt() }
                Pair(split[0], split[1])
            }
        }
        val mapStartX = 0
        val mapFinishX = 1000
        val mapStartY = 0
        val mapFinishY = walls.maxOf { it.maxOf { coordinate -> coordinate.second } } + 2
        val width = 1001
        val height = mapFinishY - mapStartY + 1
        val map = createWalls(walls, height, width, mapStartX)
        for (i in 0..1000) {
            map[map.lastIndex][i] = 1
        }
        var counter = 0
        var checker = true
        inner@ while (checker) {
            counter++
            var position = Pair(500 - mapStartX, 0)
            while (position.first in 0..mapFinishX - mapStartX && position.second <= mapFinishY) {
                try {
                    if (map[position.second + 1][position.first] == 0) {
                        position = Pair(position.first, position.second + 1)
                    } else if (map[position.second + 1][position.first - 1] == 0) {
                        position = Pair(position.first - 1, position.second + 1)
                    } else if (map[position.second + 1][position.first + 1] == 0) {
                        position = Pair(position.first + 1, position.second + 1)
                    } else {
                        map[position.second][position.first] = 2
                        if (position.first == 500 && position.second == 0) {
                            break@inner
                        }
                        continue@inner
                    }

                } catch (_: IndexOutOfBoundsException) {
                    checker = false
                    continue@inner
                }
            }
            break
        }
        return counter
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
