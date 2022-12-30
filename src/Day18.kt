import java.util.*

fun main() {
    val deltas = listOf(
        Triple(+1, 0, 0), Triple(-1, 0, 0),
        Triple(0, +1, 0), Triple(0, -1, 0),
        Triple(0, 0, +1), Triple(0, 0, -1)
    )

    fun applyDeltas(cube: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> =
        deltas.map { Triple(cube.first + it.first, cube.second + it.second, cube.third + it.third) }

    fun lavaCubes(input: List<String>) = input.map {
        val split = it.split(',').map { line -> line.toInt() }
        Triple(split[0], split[1], split[2])
    }.toSet()

    fun getAirCubes(lavaCubes: Set<Triple<Int, Int, Int>>): Set<Triple<Int, Int, Int>> {
        val airCubes = mutableSetOf<Triple<Int, Int, Int>>()
        val visitedCubes = mutableSetOf<Triple<Int, Int, Int>>()
        val queue = LinkedList<Triple<Int, Int, Int>>()
        queue.add(Triple(30, 30, 30))
        while (queue.isNotEmpty()) {
            val next = queue.pop()
            airCubes.add(next)
            visitedCubes.add(next)
            applyDeltas(next).forEach {
                if (!(kotlin.math.abs(it.first) > 30 || kotlin.math.abs(it.second) > 30 || kotlin.math.abs(it.third) > 30 || it in lavaCubes || it in visitedCubes)) {
                    queue.add(it)
                    visitedCubes.add(it)
                }
            }
        }
        return airCubes.toSet()
    }

    fun part1(input: List<String>): Int {
        var result = 0
        val lavaCubes = lavaCubes(input)
        lavaCubes.forEach { cube ->
            applyDeltas(cube).forEach {
                if (it !in lavaCubes) {
                    ++result
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val lavaCubes = lavaCubes(input)
        val airCubes = getAirCubes(lavaCubes)
        var result = 0
        lavaCubes.forEach { cube ->
            applyDeltas(cube).forEach {
                if (it in airCubes) {
                    ++result
                }
            }
        }
        return result
    }

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
