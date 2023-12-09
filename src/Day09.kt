fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val result: Int
            val tempLastNumbers = mutableListOf<Int>()
            line.split(" ").map { it.toInt() }.forEachIndexed { index, firstRowNumber ->
                if (index == 0) {
                    tempLastNumbers.add(firstRowNumber)

                } else {
                    tempLastNumbers.add(0, firstRowNumber)
                    for (k in 1 until tempLastNumbers.size) {
                        tempLastNumbers[k] = tempLastNumbers[k - 1] - tempLastNumbers[k]
                    }
                }
                //tempLastNumbers.println()
            }
            result = tempLastNumbers.sum()
            tempLastNumbers.clear()
            result
        }.reduce { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            var result = 0
            val tempLastNumbers = mutableListOf<Int>()
            line.split(" ").map { it.toInt() }.reversed().forEachIndexed { index, firstRowNumber ->
                if (index == 0) {
                    tempLastNumbers.add(firstRowNumber)

                } else {
                    tempLastNumbers.add(0, firstRowNumber)
                    for (k in 1 until tempLastNumbers.size) {
                        tempLastNumbers[k] = tempLastNumbers[k] - tempLastNumbers[k - 1]
                    }
                }
            }
            tempLastNumbers.reversed().forEach {
                result = it - result
            }
            tempLastNumbers.clear()
            result
        }.reduce { acc, i -> acc + i }
    }

    val tinput = readInput("Day09test")
    val input = readInput("Day09")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 114}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
