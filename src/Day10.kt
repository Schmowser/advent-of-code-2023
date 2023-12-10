fun main() {
    fun part1(input: List<String>): Int {
        var steps = 0
        val startingCoordinates = getStartingCoordinates(input)
        var tempCoordinates = startingCoordinates
        var tempPipe = Pipe.S
        var nextStep = Direction.SOUTH

        do {
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
        val directions = mutableListOf<Direction>()

        do {
            for (candidateDirection in tempPipe.validDirections.minus(nextStep.getCounterDirection())) { // TODO: Account for borders
                val tempNextCoordinate = input[tempCoordinates.first + candidateDirection.vertical][tempCoordinates.second + candidateDirection.horizontal]
                if (candidateDirection.getCounterDirection() in Pipe.valueOf(tempNextCoordinate.toString()).validDirections) {
                    nextStep = candidateDirection
                    directions.add(nextStep)
                    break
                }
            }
            tempCoordinates = tempCoordinates.first + nextStep.vertical to tempCoordinates.second + nextStep.horizontal
            tempPipe = Pipe.valueOf(input[tempCoordinates.first][tempCoordinates.second].toString())
            loopCoordinates.add(tempCoordinates)
        } while (tempPipe != Pipe.S)

        val sPipe = Pipe.entries.first {
            it.validDirections == setOf(directions.first(), directions.last().getCounterDirection())
        }

        val terminalOutput = input.map { it.toMutableList() }.toMutableList()

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
                        terminalOutput[inputY][inputX] = '*'
                    } else {
                        terminalOutput[inputY][inputX] = ' '
                    }
                }
                if (terminalOutput[inputY][inputX] == 'S') {
                    terminalOutput[inputY][inputX] = sPipe.name.toCharArray().first()
                }
            }
        }
        for (i in terminalOutput[0].indices) {
            if (!loopCoordinates.contains(0 to i)) {
                terminalOutput[0][i] = ' '
            }
            if (!loopCoordinates.contains(terminalOutput.size - 1 to i)) {
                terminalOutput[terminalOutput.size - 1][i] = ' '
            }
        }
        for (i in terminalOutput.indices) {
            if (!loopCoordinates.contains(i to 0)) {
                terminalOutput[i][0] = ' '
            }
            if (!loopCoordinates.contains(i to terminalOutput[0].size - 1)) {
                terminalOutput[i][terminalOutput[0].size - 1] = ' '
            }
        }

        val zoomedTerminalOutput = MutableList(2 * terminalOutput.size - 1) {
            MutableList(2 * terminalOutput[0].size - 1) { '*' }
        }
        for (a in terminalOutput.indices) {
            for (b in terminalOutput[0].indices) {
                zoomedTerminalOutput[2 * a][2 * b] = terminalOutput[a][b]
            }
        }
        for (index in zoomedTerminalOutput.first().indices) {
            if (index % 2 == 1) {
                zoomedTerminalOutput.first()[index] = ' '
            }
        }
        for (index in zoomedTerminalOutput.last().indices) {
            if (index % 2 == 1) {
                zoomedTerminalOutput.last()[index] = ' '
            }
        }
        for (index in zoomedTerminalOutput.indices) {
            if (index % 2 == 1) {
                zoomedTerminalOutput[index][0] = ' '
                zoomedTerminalOutput[index][zoomedTerminalOutput.first().size - 1] = ' '
            }
        }

        for (m in zoomedTerminalOutput.indices step 2) {
            for (n in zoomedTerminalOutput[0].indices step 2) {
                if (n + 2 < zoomedTerminalOutput[0].size) {
                    val previousPipe = zoomedTerminalOutput[m][n]
                    val currentChar = zoomedTerminalOutput[m][n + 1]
                    val nextPipe = zoomedTerminalOutput[m][n + 2]
                    //println(previousPipe + currentChar.toString() + nextPipe)
                    if (zoomedTerminalOutput[m][n] != ' ' && zoomedTerminalOutput[m][n] != '*'
                        && zoomedTerminalOutput[m][n + 2] != ' ' && zoomedTerminalOutput[m][n + 2] != '*'
                        && Pipe.valueOf(previousPipe.toString()).validDirections.contains(Direction.EAST)
                        && Pipe.valueOf(nextPipe.toString()).validDirections.contains(Direction.WEST)) {
                        zoomedTerminalOutput[m][n + 1] = '-'
                    }
                }

                if (m + 2 < zoomedTerminalOutput.size) {
                    val previousPipe = zoomedTerminalOutput[m][n]
                    val currentChar = zoomedTerminalOutput[m + 1][n]
                    val nextPipe = zoomedTerminalOutput[m + 2][n]
                    //println(previousPipe + currentChar.toString() + nextPipe)
                    if (zoomedTerminalOutput[m][n] != ' ' && zoomedTerminalOutput[m][n] != '*'
                        && zoomedTerminalOutput[m][n] != ' ' && zoomedTerminalOutput[m][n] != '*'
                        && Pipe.valueOf(previousPipe.toString()).validDirections.contains(Direction.SOUTH)
                        && Pipe.valueOf(nextPipe.toString()).validDirections.contains(Direction.NORTH)) {
                        zoomedTerminalOutput[m + 1][n] = '|'
                    }
                }
            }
        }

        var numberOfEnclosedTiles = 0
        var lastNumberOfEnclosedTiles = Int.MAX_VALUE
        while (numberOfEnclosedTiles != lastNumberOfEnclosedTiles) {
            for (inputY in 1 until zoomedTerminalOutput.size - 1) {
                for (inputX in 1 until zoomedTerminalOutput.first().size - 1) {
                    if (zoomedTerminalOutput[inputY][inputX] == '*') {
                        var hasConnectionToOutside = false
                        for (i in -1..1) {
                            for (j in -1..1) {
                                if (zoomedTerminalOutput[inputY + i][inputX + j] == ' ') {
                                    hasConnectionToOutside = true
                                }
                            }
                        }
                        if (hasConnectionToOutside) {
                            zoomedTerminalOutput[inputY][inputX] = ' '
                        }
                    }
                }
            }
            lastNumberOfEnclosedTiles = numberOfEnclosedTiles
            numberOfEnclosedTiles = zoomedTerminalOutput.flatten().count { it == '*' }
        }

        for (p in terminalOutput.indices) {
            for (q in terminalOutput.first().indices) {
                terminalOutput[p][q] = zoomedTerminalOutput[2 * p][2 * q]
            }
        }

        //zoomedTerminalOutput.map { String(it.toCharArray()).println() }
        terminalOutput.map { String(it.toCharArray()).println() }

        return terminalOutput.flatten().count { it == '*' }
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
    println("Part 2 succeeded: ${resultPart2 == 445}")
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