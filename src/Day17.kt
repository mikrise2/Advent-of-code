import kotlin.math.absoluteValue

private operator fun Set<Pair<Int, Int>>.times(point: Pair<Int, Int>): Set<Pair<Int, Int>> =
    map { Pair(it.first + point.first, it.second + point.second) }.toSet()

abstract class Shape(var x: Long, var y: Long, val map: Set<Pair<Long, Long>>) {
    abstract val width: Long
    abstract fun cells(): Set<Pair<Long, Long>>
    fun left(): Boolean {
        x--
        if (x < 0L || cells().any { it in map }) {
            x++
        }
        return true
    }

    fun right(): Boolean {
        x++
        if (x + width > 6L || cells().any { it in map }) {
            x--
        }
        return true
    }

    fun down(): Boolean {
        y--
        if (cells().any { it in map }) {
            y++
            return false
        }
        return true
    }
}

fun Set<Pair<Int, Int>>.norm(): List<Int> =
    groupBy { it.first }
        .entries
        .sortedBy { it.key }
        .map { pointList -> pointList.value.minBy { point -> point.second } }
        .let {
            val to = minOf { stone -> stone.second }
            it.map { point -> to - point.second }
        }

val shapes = listOf(
    setOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
    setOf(
        Pair(1, 0),
        Pair(0, -1),
        Pair(1, -1),
        Pair(2, -1),
        Pair(1, -2)
    ),
    setOf(
        Pair(0, 0),
        Pair(1, 0),
        Pair(2, 0),
        Pair(2, -1),
        Pair(2, -2)
    ),
    setOf(Pair(0, 0), Pair(0, -1), Pair(0, -2), Pair(0, -3)),
    setOf(Pair(0, 0), Pair(1, 0), Pair(0, -1), Pair(1, -1))
)

class HorizontalLine(x: Long, y: Long, map: Set<Pair<Long, Long>>) : Shape(x, y, map) {
    override val width: Long
        get() = 3

    override fun cells(): Set<Pair<Long, Long>> = (0L..3L).map { Pair(x + it, y) }.toSet()
}

class Plus(x: Long, y: Long, map: Set<Pair<Long, Long>>) : Shape(x, y, map) {

    override val width: Long
        get() = 2L

    override fun cells(): Set<Pair<Long, Long>> =
        setOf(Pair(x + 1L, y), Pair(x, y + 1L), Pair(x + 1L, y + 1L), Pair(x + 2L, y + 1L), Pair(x + 1L, y + 2L))
}

class ReversedL(x: Long, y: Long, map: Set<Pair<Long, Long>>) : Shape(x, y, map) {

    override val width: Long
        get() = 2L

    override fun cells(): Set<Pair<Long, Long>> =
        setOf(Pair(x, y), Pair(x + 1L, y), Pair(x + 2L, y), Pair(x + 2L, y + 1L), Pair(x + 2L, y + 2L))
}

class VerticalLine(x: Long, y: Long, map: Set<Pair<Long, Long>>) : Shape(x, y, map) {
    override val width: Long
        get() = 0L

    override fun cells(): Set<Pair<Long, Long>> =
        (0L..3L).map { Pair(x, y + it) }.toSet()
}

class Square(x: Long, y: Long, map: Set<Pair<Long, Long>>) : Shape(x, y, map) {
    override val width: Long
        get() = 1L

    override fun cells(): Set<Pair<Long, Long>> =
        setOf(Pair(x, y), Pair(x + 1L, y), Pair(x, y + 1L), Pair(x + 1L, y + 1L))
}

fun getFigure(index: Long, x: Long, y: Long, map: Set<Pair<Long, Long>>): Shape = when (index % 5) {
    0L -> HorizontalLine(x, y, map)
    1L -> Plus(x, y, map)
    2L -> ReversedL(x, y, map)
    3L -> VerticalLine(x, y, map)
    4L -> Square(x, y, map)
    else -> error("Unexpected state")
}

fun symbolIndex(input: List<String>, index: Long) = index % input[0].length


fun printMap(map: Set<Pair<Long, Long>>, maxY: Long) {
    for (y in maxY downTo 0L) {
        print('|')
        for (x in 0L..6L) {
            print(if (y == 0L) "-" else if (Pair(x, y) in map) "#" else ".")
        }
        print('|')
        println()
    }
}

fun main() {

    fun process(input: List<String>, size: Long): Long {
        var figures = 0L
        var symbolIndex = 0L
        var figureIndex = 0L
        val map = (0L..6L).map { Pair(it, 0L) }.toMutableSet()
        var maxY = 4L
        while (figures != size) {
            var index = 0L
            val figure = getFigure(figureIndex++, 2L, maxY, map)
            var checker = true
            while (checker) {
                checker = if (index++ % 2L == 1L) {
                    figure.down()
                } else {
                    if (input[0][symbolIndex(input, symbolIndex++).toInt()] == '<') figure.left()
                    else figure.right()
                }
            }
            maxY = kotlin.math.max(maxY, figure.cells().maxOf { it.second + 4L })
            figure.cells().forEach {
                map.add(it)
            }
            figures++
        }
        return maxY - 4L
    }

    fun part1(input: List<String>): Long {
        return process(input, 2022L)
    }


    fun part2(input: List<String>): Long {
        val toCoordinates: List<Pair<Int, Int>> = input[0].map { if (it == '>') Pair(1, 0) else Pair(-1, 0) }
        val map: MutableSet<Pair<Int, Int>> = (0..6).map { Pair(it, 0) }.toMutableSet()
        val up: Pair<Int, Int> = Pair(0, -1)
        val down: Pair<Int, Int> = Pair(0, 1)
        var coordinatesCount = 0
        var blocks = 0
        val stones = 999999999999L
        val visited: MutableMap<Triple<List<Int>, Int, Int>, Pair<Int, Int>> = mutableMapOf()
        while (true) {
            var current =
                shapes[(blocks++) % shapes.size].map {
                    Pair(
                        it.first + 2,
                        it.second + map.minOf { stone -> stone.second } - 4)
                }.toSet()
            do {
                val currentPhysical = current * toCoordinates[(coordinatesCount++) % toCoordinates.size]
                if (currentPhysical.all { it.first in (0..6) } && currentPhysical.intersect(map).isEmpty()) {
                    current = currentPhysical
                }
                current = current * down
            } while (current.intersect(map).isEmpty())
            map += (current * up)
            val combination = Triple(map.norm(), blocks % shapes.size, coordinatesCount % toCoordinates.size)
            if (combination in visited) {
                val start = visited.getValue(combination)
                val timed = blocks - 1L - start.first
                val total = (stones - start.first) / timed
                val remain = (stones - start.first) - (total * timed)
                val heightGainedSinceLoop = map.minOf { stone -> stone.second }.absoluteValue - start.second
                repeat(remain.toInt()) {
                    var cur = shapes[(blocks++) % shapes.size].map {
                        Pair(
                            it.first + 2,
                            it.second + map.minOf { stone -> stone.second } - 4)
                    }
                        .toSet()
                    do {
                        val curShape = cur * toCoordinates[(coordinatesCount++) % toCoordinates.size]
                        if (curShape.all { it.first in (0..6) } && curShape.intersect(map).isEmpty()) {
                            cur = curShape
                        }
                        cur = cur * down
                    } while (cur.intersect(map).isEmpty())
                    map += (cur * up)
                }
                return map.minOf { stone -> stone.second }.absoluteValue + (heightGainedSinceLoop * (total - 1))
            }
            visited[combination] = blocks - 1 to map.minOf { stone -> stone.second }.absoluteValue
        }
    }

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
