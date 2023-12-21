fun main() {
    fun buildWindowByPosition(input: List<String>, posX: Int, posY: Int): List<String> =
        input.subList(posX - 2, posX + 3)
            .map { it.substring(posY - 2, posY + 3) }

    fun getValid2Steps(window: List<String>): Set<Pair<Int, Int>> {
        assert(window.size == 5)
        assert(window[0].length == 5)
        val validPositions = mutableSetOf(0 to 0)
        if (window[2].take(2) == "..") {
            validPositions.add(0 to -2)
        }
        if (window[2].takeLast(2) == "..") {
            validPositions.add(0 to 2)
        }
        if (window[0][2] == '.' && window[1][2] == '.') {
            validPositions.add(-2 to 0)
        }
        if (window[3][2] == '.' && window[4][2] == '.') {
            validPositions.add(2 to 0)
        }
        if (window[1][1] == '.' && (window[2][1] == '.' || window[1][2] == '.')) {
            validPositions.add(-1 to -1)
        }
        if (window[1][3] == '.' && (window[2][3] == '.' || window[1][2] == '.')) {
            validPositions.add(-1 to 1)
        }
        if (window[3][1] == '.' && (window[2][1] == '.' || window[3][2] == '.')) {
            validPositions.add(1 to -1)
        }
        if (window[3][3] == '.' && (window[3][2] == '.' || window[2][3] == '.')) {
            validPositions.add(1 to 1)
        }
        return validPositions
    }

    fun part1(input: List<String>, steps: Int): Int {
        val paddedInput = mutableListOf("##${input[0].replace(".", "#")}##", "##${input[0].replace(".", "#")}##")
            .plus(input.map {"##$it##" }.toMutableList())
            .plus("##${input[0].replace(".", "#")}##")
            .plus("##${input[0].replace(".", "#")}##")
        println(paddedInput.joinToString("\n"))
        val sRow = paddedInput.indexOfFirst { it.contains("S") }
        val sCol = paddedInput[sRow].indexOf('S')
        val sPosition = sRow to sCol
        sPosition.println()
        val checkedPositions = mutableSetOf<Pair<Int, Int>>()
        val endPositions = mutableSetOf(sPosition)
        var stepCount = 0
        while (stepCount < steps) {
            for (currentPosition in endPositions.minus(checkedPositions)) {
                val tempEndPositions = mutableSetOf<Pair<Int, Int>>()
                val window = buildWindowByPosition(paddedInput, currentPosition.first, currentPosition.second)
                //println(window.joinToString("\n"))
                val nextValidSteps = getValid2Steps(window)

                for (step in nextValidSteps) {
                    tempEndPositions.add(currentPosition.first + step.first to currentPosition.second + step.second)
                }
                checkedPositions.add(currentPosition)
                endPositions.addAll(tempEndPositions)
            }

            stepCount += 2
        }

        println("Final EndPositions: ${endPositions.map { it.first - 2 to it.second - 2 }}")
        return endPositions.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day21test1")
    val input = readInput("Day21")

    val testResult1Part1 = part1(tinput, 2)
    testResult1Part1.println()
    println("Test 1 Part 1 succeeded: ${testResult1Part1 == 4}")
    val testResult2Part1 = part1(tinput, 4)
    testResult2Part1.println()
    println("Test 2 Part 1 succeeded: ${testResult2Part1 == 9}")
    val testResult3Part1 = part1(tinput, 6)
    testResult3Part1.println()
    println("Test 3 Part 1 succeeded: ${testResult3Part1 == 16}")
    val resultPart1 = part1(input, 64)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 3716}")

    //3511 too low
//
//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
