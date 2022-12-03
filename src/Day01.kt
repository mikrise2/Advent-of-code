import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var maxSum = Int.MIN_VALUE
        var tempSum = 0
        for (index in input.indices) {
            when {
                input[index] == "" -> {
                    maxSum = max(tempSum, maxSum)
                    tempSum=0
                }
                index == input.size - 1 -> {
                    maxSum = max(tempSum+input[index].toInt(), maxSum)
                }
                else -> {
                    tempSum+=input[index].toInt()
                }
            }
        }
        return maxSum
    }

    fun part2(input: List<String>): Int {
        val bests = arrayOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
        var tempSum = 0
        for (index in input.indices) {
            when {
                input[index] == "" -> {
                    bests[0] = max(tempSum,  bests[0])
                    bests.sort()
                    tempSum=0
                }
                index == input.size - 1 -> {
                    bests[0] = max(tempSum+input[index].toInt(), bests[0])
                }
                else -> {
                    tempSum+=input[index].toInt()
                }
            }
        }
        return bests.sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
