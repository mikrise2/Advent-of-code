fun getPointsForWin(enemyMove: String, yourMove: String) = when (enemyMove) {
    "A" -> {
        when (yourMove) {
            "X" -> 3
            "Z" -> 0
            else -> 6
        }
    }

    "B" -> {
        when (yourMove) {
            "Y" -> 3
            "X" -> 0
            else -> 6
        }
    }

    else -> {
        when (yourMove) {
            "Z" -> 3
            "Y" -> 0
            else -> 6
        }
    }
}

fun getPointsForItem(item: String) = when (item) {
    "X" -> 1
    "Y" -> 2
    else -> 3
}

fun getPointsFromGuessedItem(enemyMove: String, yourState: String) = when (enemyMove) {
    "A" -> when (yourState) {
        "X" -> 3
        "Y" -> 1
        else -> 2
    }

    "B" -> when (yourState) {
        "X" -> 1
        "Y" -> 2
        else -> 3
    }

    else -> when (yourState) {
        "X" -> 2
        "Y" -> 3
        else -> 1
    }
}

fun getPointsForWin(item: String) = when (item) {
    "X" -> 0
    "Y" -> 3
    else -> 6
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val moves = it.split(" ")
            getPointsForWin(moves[0], moves[1]) + getPointsForItem(moves[1])
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val moves = it.split(" ")
            getPointsFromGuessedItem(moves[0], moves[1]) + getPointsForWin(moves[1])
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
