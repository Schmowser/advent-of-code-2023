import java.math.BigInteger
import kotlin.math.abs

fun main() {
    fun calculatePathSumByExpansionRate(input: List<String>, expansionRate: Int): String {
        val rowsToDouble = mutableListOf<Int>()
        for (i in input.indices) {
            if (input[i].indices.all { input[i][it] == '.' }) {
                rowsToDouble.add(i)
            }
        }

        val colsToDouble = mutableListOf<Int>()
        for (j in input.indices) {
            if (input.indices.all { input[it][j] == '.' }) {
                colsToDouble.add(j)
            }
        }

        val galaxyCoordinates = mutableListOf<Pair<Int, Int>>()
        var expandedRowIndex = 0
        var expandedColIndex = 0
        for (a in input.indices) {
            for (b in input[a].indices) {
                if (input[a][b] == '#') {
                    galaxyCoordinates.add(expandedRowIndex to expandedColIndex)
                }
                expandedColIndex += if (b in colsToDouble) expansionRate else 1
            }
            expandedColIndex = 0
            expandedRowIndex += if (a in rowsToDouble) expansionRate else 1
        }

        var sumOfPathLengths = BigInteger.ZERO
        while (galaxyCoordinates.size > 1) {
            val source = galaxyCoordinates.removeAt(0)
            sumOfPathLengths += galaxyCoordinates.map {
                abs(it.first - source.first).toBigInteger() + abs(it.second - source.second).toBigInteger()
            }.reduce { acc, i -> acc + i }
        }

        return sumOfPathLengths.toString()
    }

    fun part1(input: List<String>): String {
        return calculatePathSumByExpansionRate(input, 2)
    }

    fun part2(input: List<String>): String {
        return calculatePathSumByExpansionRate(input, 1000000)
    }

    val tinput = readInput("Day11test")
    val input = readInput("Day11")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == "374"}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == "9563821"}")

    val testResultPart2 = calculatePathSumByExpansionRate(tinput, 10)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == "1030"}")
    val testResult2Part2 = calculatePathSumByExpansionRate(tinput, 100)
    testResult2Part2.println()
    println("Test 1 Part 2 succeeded: ${testResult2Part2 == "8410"}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == "827009909817"}")
}
