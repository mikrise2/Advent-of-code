import java.lang.Integer.min
import java.util.LinkedList

fun main() {
    fun findPath(input: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val distances = input.map { it.map { Int.MAX_VALUE }.toMutableList() }
        distances[start.second][start.first] = 0
        val path = mutableSetOf<Pair<Int, Int>>()
        val queue = LinkedList<Pair<Int, Int>>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val current = queue.poll()
            if (path.contains(current))
                continue
            val left = Pair(current.first - 1, current.second)
            val right = Pair(current.first + 1, current.second)
            val up = Pair(current.first, current.second - 1)
            val down = Pair(current.first, current.second + 1)
            if (current.first > 0 && input[current.second][current.first].code - input[current.second][current.first - 1].code >= -1
            ) {
                distances[left.second][left.first] =
                    min(distances[current.second][current.first] + 1, distances[left.second][left.first])
                queue.add(left)
            }
            if (current.first < input[0].lastIndex && input[current.second][current.first].code - input[current.second][current.first + 1].code >= -1
            ) {
                distances[right.second][right.first] =
                    min(distances[current.second][current.first] + 1, distances[right.second][right.first])
                queue.add(right)
            }
            if (current.second > 0 && input[current.second][current.first].code - input[current.second - 1][current.first].code >= -1
            ) {
                distances[up.second][up.first] =
                    min(distances[current.second][current.first] + 1, distances[up.second][up.first])
                queue.add(up)
            }
            if (current.second < input.lastIndex && input[current.second][current.first].code - input[current.second + 1][current.first].code >= -1
            ) {
                distances[down.second][down.first] =
                    min(distances[current.second][current.first] + 1, distances[down.second][down.first])
                queue.add(down)
            }
            path.add(current)
        }
        return distances[end.second][end.first]
    }

    fun part1(input: List<String>): Int {
        val startY = input.indexOfFirst { it.contains('S') }
        val startX = input[startY].indexOfFirst { it == 'S' }
        val endY = input.indexOfFirst { it.contains('E') }
        val endX = input[endY].indexOfFirst { it == 'E' }
        val copy = input.toMutableList().map { it.replace("S", "a").replace("E", "z") }
        return findPath(copy, Pair(startX, startY), Pair(endX, endY))
    }

    fun part2(input: List<String>): Int {
        val endY = input.indexOfFirst { it.contains('E') }
        val endX = input[endY].indexOfFirst { it == 'E' }
        val copy = input.toMutableList().map { it.replace("S", "a").replace("E", "z") }
        val starts = copy.filter { it.contains('a') }.mapIndexed { index, s ->
            s.mapIndexed { indexC, c ->
                if (c == 'a') Pair(indexC, index ) else Pair(
                    -1,
                    -1
                )
            }
        }.flatten().filter { it.first != -1 }
        return starts.map { findPath(copy, it, Pair(endX, endY)) }.min()
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
