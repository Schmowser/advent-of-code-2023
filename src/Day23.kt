fun main() {
    fun part1(input: List<String>): Int {
        var currentPos = 0 to 1
        var result = 0
        var comingFrom = Direction.NORTH
        var lastTile = '.'
        while (currentPos != input.size - 1 to input.last().length - 2) {

            val nextValidDirections = mutableSetOf<Direction>()
            for (dir in Direction.entries.minus(comingFrom)) {
                val nextTile = input[currentPos.first + dir.vertical].elementAt(currentPos.second + dir.horizontal)
                when {
                    nextTile == '.' -> nextValidDirections.add(dir)
                    setOf('^', '>', '<', 'v').contains(nextTile) -> nextValidDirections.add(dir)
                }
            }

            val nextDirection = nextValidDirections.first() // TODO: Split if there is a crossing
            comingFrom = nextDirection.getCounterDirection()
            currentPos = currentPos.first + nextDirection.vertical to currentPos.second + nextDirection.horizontal
            lastTile = input[currentPos.first].elementAt(currentPos.second)

            println("Stepping $nextDirection to $currentPos coming from $comingFrom tile $lastTile")

            result++
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var debugCounter = 0

        val winput = input.map { line ->
            line.replace('^', '.').replace('>', '.').replace('v', '.').replace('<', '.')
        }
        println(winput.joinToString("\n"))

        val pathList = mutableListOf<Path>()
        // currentPos, comingFrom, pathLength
        pathList.add(Path(0 to 1, Direction.NORTH, 0, mutableSetOf()))

        while (pathList.any { path -> path.currentPos != input.size - 1 to input.last().length - 2 && !path.hasLoop }) {
            if (debugCounter % 1000 == 0) {
                println(pathList.map { Pair(it.currentPos, it.pathLength) })
                println("Current Max: ${pathList.filter { !it.hasLoop }.maxOf { it.pathLength }}")
            }

            val newPaths = mutableSetOf<Path>()
            for (i in 0 until pathList.size) {
                val currentPos = pathList[i].currentPos

                if (currentPos != input.size - 1 to input.last().length - 2) {

                    val nextValidDirections = mutableSetOf<Direction>()
                    val comingFrom = pathList[i].comingFrom
                    val pathLengh = pathList[i].pathLength
                    val visitedJunctions = pathList[i].visitedJunctions

                    if (visitedJunctions.contains(currentPos)) {
                        pathList[i].hasLoop = true
                    } else {
                        val possibleNextDirections = Direction.entries.minus(comingFrom)
                        for (dir in possibleNextDirections) {
                            val nextTile = winput[currentPos.first + dir.vertical].elementAt(currentPos.second + dir.horizontal)
                            if (nextTile == '.') {
                                nextValidDirections.add(dir)
                            }
                        }

                        val isJunction = nextValidDirections.size > 1
                        val newVisitedJunctions = if (isJunction) {
                            visitedJunctions.plus(currentPos)
                        } else visitedJunctions
                        if (nextValidDirections.isNotEmpty()) {
                            pathList.removeAt(i)
                            pathList.add(
                                i,
                                Path(
                                    currentPos.first + nextValidDirections.first().vertical to currentPos.second + nextValidDirections.first().horizontal,
                                    nextValidDirections.first().getCounterDirection(),
                                    pathLengh + 1,
                                    newVisitedJunctions,

                                )
                            )

                            for (nextDirection in nextValidDirections.drop(1)) {
                                newPaths.add(
                                    Path(
                                        currentPos.first + nextDirection.vertical to currentPos.second + nextDirection.horizontal,
                                        nextDirection.getCounterDirection(),
                                        pathLengh + 1,
                                        newVisitedJunctions
                                    )
                                )
                            }
                        }
                    }
                }
            }
            pathList.removeIf { it.hasLoop }
            pathList.addAll(newPaths)
            debugCounter++
        }

        println(pathList.map { it.pathLength to it.hasLoop })
        return pathList.filter { !it.hasLoop }.maxOf { it.pathLength }
    }

    val tinput = readInput("Day23test")
    val input = readInput("Day23")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 94}")
    // The other possible hikes you could have taken were 90, 86, 82, 82, and 74 steps long.

    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 2050}")
    // Always took the first turn :D

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 154}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 9999}")

    // Takes a lot of time
    // 4999 too low
    // 9999 too high
}

class Path(
    val currentPos: Pair<Int, Int>,
    val comingFrom: Direction,
    val pathLength: Int,
    val visitedJunctions: Set<Pair<Int, Int>>,
    var hasLoop: Boolean = false,
)
