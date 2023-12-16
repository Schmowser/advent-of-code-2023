fun main() {
    fun part1(input: List<String>): Int {
        return getNumberOfEnergizedTilesByStartPosition(input, Triple(0, 0, BeamDirection.EAST))
    }

    fun part2(input: List<String>): Int {
        var maxNumberOfEnergizedTiles = 0
        BeamDirection.entries.forEach { startDirection ->
            when (startDirection) {
                BeamDirection.EAST -> {
                    for (s in input.indices) {
                        maxNumberOfEnergizedTiles = maxOf(maxNumberOfEnergizedTiles, getNumberOfEnergizedTilesByStartPosition(input, Triple(s, 0, startDirection)))
                    }
                }
                BeamDirection.NORTH -> {
                    for (s in input[0].indices) {
                        maxNumberOfEnergizedTiles = maxOf(maxNumberOfEnergizedTiles, getNumberOfEnergizedTilesByStartPosition(input, Triple(input.lastIndex, s, startDirection)))
                    }
                }
                BeamDirection.WEST -> {
                    for (s in input.indices) {
                        maxNumberOfEnergizedTiles = maxOf(maxNumberOfEnergizedTiles, getNumberOfEnergizedTilesByStartPosition(input, Triple(s, input[0].lastIndex, startDirection)))
                    }
                }
                BeamDirection.SOUTH -> {
                    for (s in input[0].indices) {
                        maxNumberOfEnergizedTiles = maxOf(maxNumberOfEnergizedTiles, getNumberOfEnergizedTilesByStartPosition(input, Triple(0, s, startDirection)))
                    }
                }
            }
        }
        return maxNumberOfEnergizedTiles
    }

    val tinput = readInput("Day16test")
    val input = readInput("Day16")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 46}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 8034}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 51}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 8225}")
}

fun getNumberOfEnergizedTilesByStartPosition(input: List<String>, startPosition: Triple<Int, Int, BeamDirection>): Int {
    val counter = Array(input.size) { Array(input[0].length) { 0 } }
    val startPositions = mutableListOf<Triple<Int, Int, BeamDirection>>()
    val pastPositions = mutableSetOf<Triple<Int, Int, BeamDirection>>()
    startPositions.add(startPosition)

    while (startPositions.isNotEmpty()) {
        var x = startPositions[0].first
        var y = startPositions[0].second
        var direction = startPositions[0].third
        startPositions.removeAt(0)

        while (x in input.indices && y in input[0].indices) {
            if (pastPositions.contains(Triple(x, y, direction))) {
                break
            } else {
                pastPositions.add(Triple(x, y, direction))
                counter[x][y]++

                when (input[x][y]) {
                    '.' -> {}
                    '\\' -> { direction = direction.getDirectionAfterBackslash() }
                    '/' -> { direction = direction.getDirectionAfterSlash() }
                    '|' -> {
                        if (direction == BeamDirection.EAST || direction == BeamDirection.WEST) {
                            val newDirection = direction.getDirectionAfterSlash()
                            startPositions.add(Triple(x + newDirection.vertical, y + newDirection.horizontal, newDirection)) // NEW DIRECTION ++, so no double counting
                            direction = direction.getDirectionAfterBackslash()
                        }
                    }
                    '-' -> {
                        if (direction == BeamDirection.NORTH || direction == BeamDirection.SOUTH) {
                            val newDirection = direction.getDirectionAfterBackslash()
                            startPositions.add(Triple(x + newDirection.vertical, y + newDirection.horizontal, newDirection))
                            direction = direction.getDirectionAfterSlash()
                        }
                    }
                    else -> {}
                }
                x += direction.vertical
                y += direction.horizontal
            }
        }
    }

    //println(counter.joinToString(separator = "\n") { it.joinToString() })
    return counter.sumOf { it.map { n -> if (n == 0) 0 else 1 }.sum() }
}

enum class BeamDirection(
    val vertical: Int,
    val horizontal: Int,
) {
    NORTH(-1, 0),
    EAST(0, 1),
    SOUTH(1, 0),
    WEST(0, -1);

    fun getDirectionAfterBackslash(): BeamDirection {
        return when(this) {
            NORTH -> WEST
            EAST -> SOUTH
            SOUTH -> EAST
            WEST -> NORTH
        }
    }

    fun getDirectionAfterSlash(): BeamDirection {
        return when(this) {
            NORTH -> EAST
            EAST -> NORTH
            SOUTH -> WEST
            WEST -> SOUTH
        }
    }
}