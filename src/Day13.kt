fun chunkStringToList(str: String): List<String> {
    val withoutBrackets = str.filterIndexed { index, _ -> index != 0 && index != str.lastIndex }
    val elements = mutableListOf<String>()
    var bracketIndex = 0
    val current = StringBuilder("")
    for (i in withoutBrackets.indices) {
        if ((withoutBrackets[i] == ',' && bracketIndex == 0)) {
            elements.add(current.toString())
            current.clear()
        } else {
            current.append(withoutBrackets[i])
            if (withoutBrackets[i] == '[') {
                bracketIndex++
            }
            if (withoutBrackets[i] == ']') {
                bracketIndex--
            }
        }
    }
    if (current.isNotEmpty())
        elements.add(current.toString())
    return elements
}

fun compareLists(first: String, second: String): Int {
    val elements1 = chunkStringToList(first)
    val elements2 = chunkStringToList(second)
    if (elements1.isEmpty() && elements2.isNotEmpty())
        return 1
    if (elements1.isEmpty())
        return 0
    if (elements2.isEmpty())
        return -1
    elements1.forEachIndexed { index, s ->
        if (elements2.size <= index)
            return -1
        val comp = compare(s, elements2[index])
        if (comp != 0) {
            return comp
        }
    }
    if (elements1.size == elements2.size)
        return 0
    return 1
}

fun compareNumbers(first: Int, second: Int): Int {
    return if (first == second) 0 else if (first < second) 1 else -1
}

fun compareNumberList(number: Int, list: String): Int {
    return compareLists("[$number]", list)
}

fun compareNumberList(list: String, number: Int) = -compareNumberList(number, list)

fun compare(first: String, second: String): Int {
    return if (first[0] == '[') {
        if (second[0] == '[') compareLists(first, second) else compareNumberList(first, second.toInt())
    } else {
        if (second[0] == '[') compareNumberList(first.toInt(), second) else compareNumbers(
            first.toInt(),
            second.toInt()
        )
    }
}

class PackageComparator {
    companion object : Comparator<String> {
        override fun compare(a: String, b: String): Int {
            return -compareLists(a, b)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input.chunked(3).map { if (it.size == 3) it.subList(0, 2) else it }
        var result = 0
        pairs.forEachIndexed { index, strings -> if (compareLists(strings[0], strings[1]) >= 0) result += (index + 1) }
        return result
    }

    fun part2(input: List<String>): Int {
        val index1 = "[[2]]"
        val index2 = "[[6]]"
        val packages = input.filter { it.isNotEmpty() } + listOf(
            index1,
            index2
        )
        val sortedPackages = packages.sortedWith(PackageComparator)
        return (sortedPackages.indexOf(index1)+1) * (sortedPackages.indexOf(index2)+1)
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
