fun main() {
    fun part1(input: List<String>): Int {
        var steps = 0

        val counter = Array(input.size) { Array(input[0].length) { Int.MAX_VALUE } }
        counter[input.lastIndex][input[0].lastIndex] = 0
        var nextPositionWithValidDirections = mutableListOf(PreviousStep(input.lastIndex, input[0].lastIndex, listOf(Direction.NORTH, Direction.WEST)))

        while (nextPositionWithValidDirections.isNotEmpty()) { //
            //println("Next: $nextPositionWithValidDirections")
            val next = nextPositionWithValidDirections.removeAt(0)
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
                            Direction.entries.minus(direction.getCounterDirection())
                        }
                        nextPositionWithValidDirections.add(
                            PreviousStep(nextRow, nextCol, nextValidDirections, next.directionHistory.plus(direction))
                        )
                    }
                }
            }
            if (steps % 500_000 == 0) {
                println("After $steps Steps, ${nextPositionWithValidDirections.size} next positions open")
            }
            steps++
        }

        println(counter.joinToString(separator = "\n") { it.joinToString() })
        println("After $steps Steps, ${nextPositionWithValidDirections.size} next positions open")
        return counter[0][0]
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day17test2")
    val input = readInput("Day17")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 102}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")
//
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