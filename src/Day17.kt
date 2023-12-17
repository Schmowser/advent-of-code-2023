fun main() {
    fun part1(tinput: List<String>): Int {
        var tempCounter = Array(1) { Array(1) { 0 } }

        for (i in tinput.indices.take(tinput.size - 1).reversed()) {
            val inputMinusRows = tinput.toMutableList().subList(i, tinput.lastIndex + 1)
            val inputMinusRowsAndCols = inputMinusRows
                .map { it.substring(i, it.lastIndex + 1) }
            println(inputMinusRowsAndCols[0])
            println("${inputMinusRowsAndCols.size} | ${inputMinusRowsAndCols[0].length}")
            val tempResult = subPart1(inputMinusRowsAndCols, inputMinusRowsAndCols.lastIndex, inputMinusRowsAndCols[0].lastIndex, tempCounter)

            tempCounter = tempResult.third
            println("Min at (${tempResult.first}, ${tempResult.second}) with counter size ${tempCounter.size}")
            println()
        }
        return tempCounter[0][0] - tinput[tinput.lastIndex][tinput[0].lastIndex].toString().toInt()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day17test")
    val input = readInput("Day17")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 102}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

    // 922 too high

//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}

class PreviousStep(
    val row: Int,
    val col: Int,
    val validDirections: List<Direction>,
    val directionHistory: List<Direction> = mutableListOf()
)

fun List<Direction>.areLastThreeDirectionsEqual(): Boolean {
    val last3 = this.takeLast(3)
    return last3.size == 3 && last3.distinct().size == 1
}

fun List<Direction>.areLastThreeDirectionsDistinct(): Boolean {
    val last3 = this.takeLast(3)
    return last3.size == 3 && last3.distinct().size == 3
}

fun subPart1(input: List<String>, startRow: Int, startCol: Int, subCounter: Array<Array<Int>>): Triple<Int, Int, Array<Array<Int>>> {
    val nextPositionWithValidDirections = mutableListOf(
        PreviousStep(startRow, startCol, Direction.entries)
    )
    val pastPositionsWithValidDirections = mutableListOf<Pair<Int, Int>>()
    var steps = 0
    val counter = Array(input.size) { Array(input[0].length) { Int.MAX_VALUE } }
    for (i in subCounter.indices) {
        for (j in subCounter[0].indices) {
            counter[i + 1][j + 1] = subCounter[i][j]
        }
    }

    while (nextPositionWithValidDirections.isNotEmpty()) { //
        //println("Next: $nextPositionWithValidDirections")
        val next = nextPositionWithValidDirections.removeAt(0)
        if (pastPositionsWithValidDirections.count { it.first == next.row && it.second == next.col } <= 4) {
            for (direction in next.validDirections) {
                val nextRow = next.row + direction.vertical
                val nextCol = next.col + direction.horizontal
                if (nextRow in input.indices && nextCol in input[0].indices) {
                    counter[nextRow][nextCol] = minOf(
                        input[nextRow][nextCol].toString().toInt() + counter[next.row][next.col],
                        counter[nextRow][nextCol]
                    )

                    if (input[nextRow][nextCol].toString().toInt() + counter[next.row][next.col] == counter[nextRow][nextCol]) {
                        val nextValidDirections = if (next.directionHistory.plus(direction).areLastThreeDirectionsEqual()) {
                            Direction.entries.minus(setOf(direction, direction.getCounterDirection()))
                        } else {
                            if (next.directionHistory.plus(direction).areLastThreeDirectionsDistinct()) {
                                Direction.entries.minus(next.directionHistory.plus(direction).toSet()).map { it.getCounterDirection() }
                            } else {
                                Direction.entries.minus(direction.getCounterDirection())
                            }
                        }
                        nextPositionWithValidDirections.add(
                            PreviousStep(nextRow, nextCol, nextValidDirections, next.directionHistory.plus(direction))
                        )
                        pastPositionsWithValidDirections.add(next.row to next.col)

                    }
                }
            }
            //        if (steps % 500_000 == 0) {
            //            println("After $steps Steps, ${nextPositionWithValidDirections.size} next positions open")
            //        }
            steps++
        }
    }

    println(counter.joinToString(separator = "\n") { it.joinToString() })
    println("After $steps Steps, ${nextPositionWithValidDirections.size} next positions open")
    var minTopRowPosition = Int.MAX_VALUE to 0
    var minLeftColPosition = Int.MAX_VALUE to 0
    for (i in input.indices) {
        if (counter[i][0] < minLeftColPosition.first) {
            minLeftColPosition = counter[i][0] to i
        }
    }
    for (j in input[0].indices) {
        if (counter[0][j] < minTopRowPosition.first) {
            minTopRowPosition = counter[0][j] to j
        }
    }

    return if (minTopRowPosition.first < minLeftColPosition.first) {
        Triple(0, minTopRowPosition.second, counter)
    } else {
        Triple(minLeftColPosition.second, 0, counter)
    }
}