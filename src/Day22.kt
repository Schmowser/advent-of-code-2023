fun main() {
    fun part1(input: List<String>): Int {
        val sortedInput = input.map { line ->
            val firstSecond = line.split("~")
            val first = firstSecond[0].split(",")
            val second = firstSecond[1].split(",")
            Triple(first[0].toInt()..second[0].toInt(), first[1].toInt()..second[1].toInt(), first.last().toInt())
        }.sortedBy { it.third }
        println(sortedInput)

        val fallenDownBricks = sortedInput.mapIndexed { index, brick ->
            if (index > 0) {
                // TODO: Not only check sortedInput[index - 1] but all the bricks before
                val intersectX = sortedInput[index - 1].first.intersect(sortedInput[index].first)
                val intersectY = sortedInput[index - 1].second.intersect(sortedInput[index].second)
                println(intersectX)
                println(intersectY)
                println("----")

                if (intersectX.isNotEmpty() && intersectY.isNotEmpty()) {
                    Triple(brick.first, brick.second, sortedInput[index - 1].third + 1)
                } else {
                    Triple(brick.first, brick.second, sortedInput[index - 1].third)
                }
            } else brick
        }
        fallenDownBricks.println()

        // TODO: Add the intersections to the currentBrick

        // TODO: Filter for the bricks that are not the only support for any other brick
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day22test1")
    val input = readInput("Day22")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 5}")
//    val resultPart1 = part1(input)
//    resultPart1.println()
//    println("Part 1 succeeded: ${resultPart1 == 114400}")
//
//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
