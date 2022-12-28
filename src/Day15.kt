import kotlin.math.abs
import kotlin.math.absoluteValue

class UnRange constructor(private var list: List<IntRange>) {
    constructor(r: IntRange) : this(listOf(r))
    fun minus(s: IntRange): UnRange {
        if (s.isEmpty()) {
            return this
        }
        val ranges = ArrayList<IntRange>(list.size + 2)
        list.forEachIndexed {index, range->
            val end = s.last + 1
            val start = s.first - 1
            if (range.contains(start) || range.contains(end)) {
                if (range.contains(start)) {
                    ranges.add(range.first..start)
                }
                if (range.contains(end)) {
                    ranges.add(end..range.last)
                }
            } else if (!s.contains(range.first) || !s.contains(range.last)) {
                ranges.add(range)
            }
        }
        list = ranges
        return this
    }
    fun first() = list.first().first
    fun sum() = list.sumOf { it.last - it.first + 1 }
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) =
    abs(this.first - other.first) + abs(this.second - other.second)

fun main() {
    fun part1(input: List<String>): Int {
        val sensors = input.map {
            val split = it.split(" ")
            val xS = split[2].substring(2, split[2].lastIndex).toInt()
            val yS = split[3].substring(2, split[3].lastIndex).toInt()
            val xB = split[8].substring(2, split[8].lastIndex).toInt()
            val yB = split[9].substring(2, split[9].length).toInt()
            listOf(Pair(xS, yS), Pair(xB, yB))
        }
        val map = mutableSetOf<Int>()
        sensors.forEach {
            val distance = it[0] - it[1]
            val remainder = distance - kotlin.math.abs(it[0].second - 2000000)
            if (remainder > 0) {
                map += (it[0].first..(it[0].first + remainder)).toSet()
                map += (it[0].first downTo (it[0].first - remainder)).toSet()
            }
        }
        return map.minus(sensors.filter { it[1].second == 2000000 }.map { it[1].first }.toSet()).size
    }

    fun coveredRange(point: Pair<Int, Int>, radius: Int, targetY: Int): IntRange? {
        val yOff = (targetY - point.second).absoluteValue
        val rowRadius = radius - yOff
        return if (yOff > radius) {
            null
        } else {
            point.first - rowRadius..point.first + rowRadius
        }
    }

    fun part2(input: List<String>): Long {
        val sensors = input.map {
            val split = it.split(" ")
            val xS = split[2].substring(2, split[2].lastIndex).toInt()
            val yS = split[3].substring(2, split[3].lastIndex).toInt()
            val xB = split[8].substring(2, split[8].lastIndex).toInt()
            val yB = split[9].substring(2, split[9].length).toInt()
            listOf(Pair(xS, yS), Pair(xB, yB))
        }
        var expectedX: Int? = null
        var expectedY: Int? = null
        val range = 0..4000000
        for (y in range) {
            val expected = UnRange(range)
            for ((s, b) in sensors) {
                val coveredRange = coveredRange(s, s-b, y)
                if (coveredRange != null) {
                    expected.minus(coveredRange)
                }
            }
            val count = expected.sum()
            if (count == 1) {
                expectedY = y
                expectedX = expected.first()
                break
            }
        }
        return expectedX!! * 4000000L + expectedY!!
    }

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
