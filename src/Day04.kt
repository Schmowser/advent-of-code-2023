fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val splitLine = line.split(": ", " | ")
            val winningNumbers = splitLine[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val matchingNumbersInHand = splitLine[2].split(" ")
                .filter { it.isNotBlank() }
                .sumOf { handNumber ->
                    winningNumbers.any { it == handNumber.toInt() }.compareTo(false)
                }.toDouble()
            if (matchingNumbersInHand != 0.0) {
                Math.pow(2.0, matchingNumbersInHand - 1)
            } else 0.0
        }.reduce { a, b -> a + b }.toInt()
    }

    fun part2(input: List<String>): Int {
        val numberOfCardMap = (1..input.size).associateWith { 1 }.toMutableMap()
        input.forEach { line ->
            val splitLine = line.split(": ", " | ")
            val cardNumber = splitLine[0].split(" ").last().toInt()
            val winningNumbers = splitLine[1].split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val matchingNumbersInHand = splitLine[2].split(" ")
                .filter { it.isNotBlank() }
                .sumOf { handNumber ->
                    winningNumbers.any { it == handNumber.toInt() }.compareTo(false)
                }
            if (matchingNumbersInHand != 0) {
                ((cardNumber + 1)..(cardNumber + matchingNumbersInHand)).forEach {
                    numberOfCardMap[it] = numberOfCardMap[it]?.plus(numberOfCardMap[cardNumber] ?: 1) ?: 1
                }
            }
        }
        return numberOfCardMap.values.sum()
    }

    val tinput = readInput("Day04test")
    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 13}")
    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 30}")


    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
