fun main() {
    fun part1(input: List<String>): Int {
        val loadList = mutableListOf<Int>()
        repeat(input[0].length) {
            loadList.add(input.size)
        }

        var load = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                when (input[i][j]) {
                    'O' -> {
                        load += loadList[j]
                        loadList[j]--
                    }
                    '#' -> {
                        loadList[j] = input.size - i - 1
                    }
                    else -> {}
                }
            }
        }
        return load
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day14test")
    val input = readInput("Day14")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 136}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
