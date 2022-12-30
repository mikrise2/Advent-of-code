fun mix(list: List<Pair<Long, Long>>): List<Pair<Long, Long>> {
    val mixedList = list.toMutableList()
    val size = list.size
    repeat(size) { index ->
        val currentIndex = mixedList.indexOfFirst { it.first == index.toLong() }
        val elem = mixedList.removeAt(currentIndex)
        val newIndex = (currentIndex + elem.second) % (size - 1)
        mixedList.add((newIndex + if (newIndex <= 0) (size - 1) else 0).toInt(), elem)
    }
    return mixedList
}

fun main() {
    fun part1(input: List<String>): Long {
        val list = mix(input.mapIndexed { index, it ->
            Pair(index.toLong(), it.toLong())
        }.toList())
        val zeroIndex = list.indexOfFirst { it.second == 0L }.toLong()
        val size = list.size.toLong()
        return listOf(1000L, 2000L, 3000L).sumOf { list[((zeroIndex + it) % size).toInt()].second }
    }

    fun part2(input: List<String>): Long {
        var list = input.mapIndexed { index, it ->
            Pair(index.toLong(), it.toLong())
        }.toList().map { Pair(it.first, it.second * 811589153L) }
        repeat(10) {
            list = mix(list)
        }
        val zeroIndex = list.indexOfFirst { it.second == 0L }.toLong()
        val size = list.size.toLong()
        return listOf(1000L, 2000L, 3000L).sumOf { list[((zeroIndex + it) % size).toInt()].second }
    }

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
