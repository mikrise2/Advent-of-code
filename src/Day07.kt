data class File(val name: String, val size: Int)

class Directory(private val parent: Directory? = null) {
    private val files = mutableListOf<File>()
    var amountSize = 0
        private set

    fun addFile(file: File) {
        files.add(file)
        increaseSize(file.size)
    }

    private fun increaseSize(size: Int) {
        amountSize += size
        parent?.increaseSize(size)
    }
}

fun backDirectory(path: String): String {
    val index = path.indexOfLast { it == '/' }
    return path.substring(0, index)
}

fun main() {

    fun getDirectories(input: List<String>): Map<String, Directory> {
        val directories = mutableMapOf<String, Directory>()
        directories["/"] = Directory()
        var currentDirectory = "/"
        input.forEach {
            val command = it.split(" ")
            if (it[0] == '$') {
                if (command[1] == "cd") {
                    when (command[2]) {
                        "/" -> currentDirectory = "/"
                        ".." -> currentDirectory = backDirectory(currentDirectory)
                        else -> {
                            val newPath = "$currentDirectory/${command[2]}"
                            if (!directories.contains(newPath))
                                directories["$currentDirectory/${command[2]}"] =
                                    Directory(directories[currentDirectory])
                            currentDirectory += "/${command[2]}"
                        }
                    }
                }
            } else {
                if (command[0] != "dir") {
                    directories[currentDirectory]?.addFile(File(command[1], command[0].toInt()))
                }
            }
        }
        return directories
    }

    fun part1(input: List<String>): Int {
        return getDirectories(input).values.filter { it.amountSize <= 100000 }.sumOf { it.amountSize }
    }

    fun part2(input: List<String>): Int {
        val directories = getDirectories(input)
        val freeSpace = 70000000 - (directories["/"]?.amountSize ?: 0)
        val needToFree = 30000000 - freeSpace
        return directories.values.sortedBy { it.amountSize }.first { it.amountSize>=needToFree }.amountSize
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
