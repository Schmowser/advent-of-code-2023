fun main() {
    fun part1(input: List<String>): Int {
        var searchIndex: Int
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
        var searchIndex: Int
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
        return specialChars.filter { it.first == "*" }
            .map { gear ->
                partNumbers.filter { partNumber ->
                    gear.second in (partNumber.lineIndex - 1)..(partNumber.lineIndex + 1) && gear.third in (partNumber.startIndex - 1) ..(partNumber.endIndex)
                }.takeIf { it.size > 1 }
                    ?.fold(1) { acc: Int, partNumber: PartNumber -> acc * partNumber.value }
                    ?: 0
            }.sum()
    }

    val tinput = readInput("Day03test")
    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 4361}")
    // TODO: Missing 7 from row 34 and 3 from row 97

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 467835}")

    val input = readInput("Day03")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Succeeded: ${resultPart1 == 557705}")
    val resultPart2 = part2(input)
    (resultPart2).println()
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