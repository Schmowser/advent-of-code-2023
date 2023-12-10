fun main() {
    fun part1(input: List<String>): Int {
        var steps = 0
        val startingCoordinates = getStartingCoordinates(input)
        var tempCoordinates = startingCoordinates
        var tempPipe = Pipe.S
        var nextStep = Direction.SOUTH

        do {
            println("$tempPipe on $tempCoordinates")
            for (candidateDirection in tempPipe.validDirections.minus(nextStep.getCounterDirection())) {
                val tempNextCoordinate = input[tempCoordinates.first + candidateDirection.vertical][tempCoordinates.second + candidateDirection.horizontal]
                if (candidateDirection.getCounterDirection() in Pipe.valueOf(tempNextCoordinate.toString()).validDirections) {
                    nextStep = candidateDirection
                    break
                }
            }
            steps++
            tempCoordinates = tempCoordinates.first + nextStep.vertical to tempCoordinates.second + nextStep.horizontal
            tempPipe = Pipe.valueOf(input[tempCoordinates.first][tempCoordinates.second].toString())
        } while (tempPipe != Pipe.S)

        return steps / 2
    }

    fun part2(input: List<String>): Int {
        val loopCoordinates = mutableListOf<Pair<Int, Int>>()
        val startingCoordinates = getStartingCoordinates(input)
        var tempCoordinates = startingCoordinates
        var tempPipe = Pipe.S
        var nextStep = Direction.SOUTH

        do {
            for (candidateDirection in tempPipe.validDirections.minus(nextStep.getCounterDirection())) { // TODO: Account for borders
                val tempNextCoordinate = input[tempCoordinates.first + candidateDirection.vertical][tempCoordinates.second + candidateDirection.horizontal]
                if (candidateDirection.getCounterDirection() in Pipe.valueOf(tempNextCoordinate.toString()).validDirections) {
                    nextStep = candidateDirection
                    break
                }
            }
            tempCoordinates = tempCoordinates.first + nextStep.vertical to tempCoordinates.second + nextStep.horizontal
            tempPipe = Pipe.valueOf(input[tempCoordinates.first][tempCoordinates.second].toString())
            loopCoordinates.add(tempCoordinates)
        } while (tempPipe != Pipe.S)

        // TODO: Find subloops in loopCoordinates

        loopCoordinates.println()

        // DEBUGGING
        val terminalOutput = input.map { it.toMutableList() }.toMutableList()

        var numberOfEnclosedTiles = 0
        val tileSize = input.size to input[0].length // Assuming a square

        for (inputY in 1 until tileSize.first - 1) {
            for (inputX in 1 until tileSize.second - 1) {
                if (!loopCoordinates.contains(inputY to inputX)) {
                    val pipesHorizontal = loopCoordinates.filter { it.first == inputY }
                    val enclosedHorizontal = ((pipesHorizontal.size >= 2)
                                              && (inputX > (pipesHorizontal.minOfOrNull { it.second }?: Int.MAX_VALUE))
                                              && (inputX < (pipesHorizontal.maxOfOrNull { it.second } ?: Int.MIN_VALUE)))

                    val pipesVertical = loopCoordinates.filter { it.second == inputX }
                    val enclosedVertical = pipesVertical.size >= 2
                                           && (inputY > (pipesVertical.minOfOrNull { it.first }?: Int.MAX_VALUE))
                                           && (inputY < (pipesVertical.maxOfOrNull { it.first } ?: Int.MIN_VALUE))

                    loopCoordinates.filter { it.first == inputY }

                    if (enclosedVertical && enclosedHorizontal) {
                        //println("${inputY to inputX} | pipes on horizontal: $pipesHorizontal | pipes on vertical $pipesVertical")
                        terminalOutput[inputY][inputX] = '*'
                        numberOfEnclosedTiles++ // TODO: Differentiate between Inside and Outside
                    }
                }
            }
        }

        // Everything after this is in red

        // Resets previous color codes
        terminalOutput.map { String(it.toCharArray()).println() }
        return numberOfEnclosedTiles
    }

    val tinput = readInput("Day10test")
    val tinput2 = readInput("Day10test2")
    val tinput3 = readInput("Day10test3")
    val tinput4 = readInput("Day10test4")
    val tinput5 = readInput("Day10test5")
    val input = readInput("Day10")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 4}")
    val test2ResultPart1 = part1(tinput2)
    test2ResultPart1.println()
    println("Test 2 Part 1 succeeded: ${test2ResultPart1 == 8}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 1}")
    val test2ResultPart2 = part2(tinput3)
    test2ResultPart2.println()
    println("Test 2 Part 2 succeeded: ${test2ResultPart2 == 4}")
    val test3ResultPart2 = part2(tinput4)
    test3ResultPart2.println()
    println("Test 3 Part 2 succeeded: ${test3ResultPart2 == 8}")
    val test4ResultPart2 = part2(tinput5)
    test4ResultPart2.println()
    println("Test 3 Part 2 succeeded: ${test4ResultPart2 == 10}")


    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 525}") // Computes 741
}

fun getStartingCoordinates(input: List<String>): Pair<Int, Int> {
    for (i in input.indices) {
        for (j in input[i].indices) {
            if (input[i][j] == Pipe.S.name.toCharArray()[0]) {
                return i to j
            }
        }
    }
    return Int.MAX_VALUE to Int.MAX_VALUE
}

enum class Pipe(
    val validDirections: Set<Direction>
) {
    `S`(setOf(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)),
    `F`(setOf(Direction.EAST, Direction.SOUTH)),
    `-`(setOf(Direction.EAST, Direction.WEST)),
    `7`(setOf(Direction.WEST, Direction.SOUTH)),
    `|`(setOf(Direction.NORTH, Direction.SOUTH)),
    `J`(setOf(Direction.NORTH, Direction.WEST)),
    `L`(setOf (Direction.NORTH, Direction.EAST));
}

enum class Direction(
    val vertical: Int,
    val horizontal: Int,
) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    fun getCounterDirection(): Direction {
        return when(this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }
}