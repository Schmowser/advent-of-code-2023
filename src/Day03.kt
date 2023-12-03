fun main() {
    fun part1(input: List<String>): Int {
        var searchIndex = 0
        val partNumbers = input.mapIndexed { lineIndex, line ->
            searchIndex = 0
            line.split(Regex("\\D+")).filterNot { it.isEmpty() }
                .map {
                    val startIndex = line.indexOf(it, searchIndex)
                    searchIndex = startIndex + 1
                    PartNumber(it.toInt(), lineIndex, startIndex, startIndex + it.length)
                }
        }.flatten()
        val specialCharSearchIndex = mutableMapOf<String, Int>()
        val specialChars = input.mapIndexed { index, line ->
            specialCharSearchIndex.clear()
            line.split(Regex("\\.+")).map { it.replace(Regex("[0-9]"), "") }.filterNot { it.isEmpty() }
                .map {
                    val startIndex = line.indexOf(it, specialCharSearchIndex[it] ?: 0)
                    specialCharSearchIndex[it] = startIndex + 1
                    Triple(it, index, startIndex)
                }
        }.flatten()
        return partNumbers.filter { partNumber ->
            specialChars.any {
                it.second in (partNumber.lineIndex - 1)..(partNumber.lineIndex + 1) && it.third in (partNumber.startIndex - 1) ..(partNumber.endIndex)
            }
        }.also { println(it.map { it.value }) }.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day03test")
    val testResult = part1(tinput)
    testResult.println()
    println("Test succeeded: ${testResult == 4361}")

    val input = readInput("Day03")
    part1(input).println()
    //part2(input).println()
}

private class PartNumber(
    val value: Int,
    val lineIndex: Int,
    val startIndex: Int,
    val endIndex: Int,
) {
    override fun toString(): String {
        return "($value, l$lineIndex, $startIndex)"
    }
}