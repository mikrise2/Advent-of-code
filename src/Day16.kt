import com.github.shiguruikai.combinatoricskt.combinations
import java.lang.Integer.min

data class Valve(
    val name: String,
    val rate: Int,
    val ways: List<String>,
    var isOpened: Boolean = false,
    var waysDistances: MutableMap<String, Int> = mutableMapOf()
)

fun wayDistances(start: String, current: String, visited: Set<String>, valves: Map<String, Valve>, wayLength: Int = 0) {
    if (current in visited)
        return
    if (current == start && wayLength != 0)
        return
    if (valves[current]!!.rate != 0)
        if (!valves[start]!!.waysDistances.contains(current))
            valves[start]!!.waysDistances[current] = wayLength
        else
            valves[start]!!.waysDistances[current] = min(valves[start]!!.waysDistances[current]!!, wayLength)

    valves[current]!!.ways.forEach {
        wayDistances(start, it, visited + setOf(current), valves, wayLength + 1)
    }
}

fun find(
    valves: Map<String, Valve>,
    remainingTime: Int,
    visited: Set<String>,
    current: String,
    total: Int
): Int {
    val candidates =
        valves.filter { it.key in valves[current]!!.waysDistances && it.key !in visited }.asSequence()
            .filter { remainingTime - valves[current]!!.waysDistances[it.key]!! - 1 > 0 }
    return candidates.maxOfOrNull {
        val rateTime = remainingTime - valves[current]!!.waysDistances[it.key]!! - 1
        find(
            valves,
            rateTime,
            visited + setOf(it.key),
            it.key,
            total + ((rateTime) * valves[it.key]!!.rate)
        )
    } ?: total
}

fun main() {

    fun part1(input: List<String>): Int {
        val valves = mutableMapOf<String, Valve>()
        input.forEach {
            val split = it.split(" ")
            val name = split[1]
            val rate = split[4].substring(5, split[4].lastIndex).toInt()
            val ways = split.subList(9, split.size).map { way -> way.replace(",", "") }
            valves[name] = Valve(name, rate, ways)
        }
        val start = "AA"
        valves.keys.forEach {
            wayDistances(it, it, emptySet(), valves)
        }
        val startPosition = valves.values.filter { it.rate == 0 || it.name == "AA" }.map { it.name }.toSet()
        startPosition.forEach { flow ->
            valves.values.forEach { it.waysDistances.remove(flow) }
        }
        val starts = valves[start]!!.waysDistances.keys
        valves.values.forEach {
            it.waysDistances = it.waysDistances.filter { way -> way.key in starts || way.key == start }.toMutableMap()
        }
        return find(valves, 30, emptySet(), start, 0)
    }

    fun part2(input: List<String>): Int {
        val valves = mutableMapOf<String, Valve>()
        input.forEach {
            val split = it.split(" ")
            val name = split[1]
            val rate = split[4].substring(5, split[4].lastIndex).toInt()
            val ways = split.subList(9, split.size).map { way -> way.replace(",", "") }
            valves[name] = Valve(name, rate, ways)
        }
        val start = "AA"
        valves.keys.forEach {
            wayDistances(it, it, emptySet(), valves)
        }
        val startPosition = valves.values.filter { it.rate == 0 || it.name == "AA" }.map { it.name }.toSet()
        startPosition.forEach { flow ->
            valves.values.forEach { it.waysDistances.remove(flow) }
        }
        val starts = valves[start]!!.waysDistances.keys
        valves.values.forEach {
            it.waysDistances = it.waysDistances.filter { way -> way.key in starts || way.key == start }.toMutableMap()
        }
        val combinations = starts.filterNot { it == start }.combinations(starts.size / 2)
        return combinations.maxOf {
            find(valves, 26, it.toSet(), start, 0) + find(valves, 26, starts.toSet() - it.toSet(), start, 0)
        }
    }

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
