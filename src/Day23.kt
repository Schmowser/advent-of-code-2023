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
        val winput = input.map { line ->
            line.replace('^', '.').replace('>', '.').replace('v', '.').replace('<', '.')
        }
        println(winput.joinToString("\n"))

        var result = 0
        val pathList = mutableListOf<Triple<Pair<Int, Int>, Direction, Int>>()
        // currentPos, comingFrom, pathLength
        pathList.add(Triple(0 to 1, Direction.NORTH, 0))

        while (pathList.any { path -> path.first != input.size - 1 to input.last().length - 2 }) {
            for (i in 0 until pathList.size) {
                val currentPos = pathList[i].first
                val nextValidDirections = mutableSetOf<Direction>()
                val comingFrom = pathList[i].second
                for (dir in Direction.entries.minus(comingFrom)) {
                    val nextTile = input[currentPos.first + dir.vertical].elementAt(currentPos.second + dir.horizontal)
                    when {
                        nextTile == '.' -> nextValidDirections.add(dir)
                        setOf('^', '>', '<', 'v').contains(nextTile) -> nextValidDirections.add(dir)
                    }
                }

                val nextDirection = nextValidDirections.first() // TODO: Split if there is a crossing
                // TODO: Do not go where we already were

                pathList[i] = Triple(
                    currentPos.first + nextDirection.vertical to currentPos.second + nextDirection.horizontal,
                    nextDirection.getCounterDirection(),
                    result++
                )

                println("Stepping $nextDirection to $currentPos coming from $comingFrom")
            }
        }

        println(pathList)
        return pathList.maxOf { it.third }
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
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}

fun Char.toDirection(): Direction = when (this) {
    '^' -> Direction.NORTH
    '>' -> Direction.EAST
    'v' -> Direction.SOUTH
    '<' -> Direction.WEST
    else -> throw IllegalStateException()
}