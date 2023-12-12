fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it ->
            it.println()

            val (line, springNumberString) = it.split(" ").let { it[0] to it[1] }
            var listOfPossibleArrangements = listOf("")
            for (char in line) {
                if (char == '.' || char == '#') {
                    listOfPossibleArrangements = listOfPossibleArrangements.map { it + char }
                } else if (char == '?') {
                    listOfPossibleArrangements = listOfPossibleArrangements.map { listOf("$it.", "$it#") }.flatten()
                }
            }
            //listOfPossibleArrangements.println()

            val springNumbers = springNumberString.split(",").map { it.toInt() }
            val totalSprings = springNumbers.sum()
            listOfPossibleArrangements = listOfPossibleArrangements.filter { it.count { it == '#' } == totalSprings }
            //listOfPossibleArrangements.println()

            val listOfValidArrangement = listOfPossibleArrangements.filter {
                val springGroupSize = it.split(".").filter { it.contains('#') }.map { it.length }
                //println("$springGroupSize | $springNumbers")
                springGroupSize == springNumbers
            }
            println("${listOfValidArrangement.size}")

            listOfValidArrangement.size
        }.reduce { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        val unfoldedInput = input.map {
            val (line, springNumberString) = it.split(" ").let { it[0] to it[1] }
            val newLine = mutableListOf<String>()
            repeat(5) {
                newLine.add("$line?")
            }
            newLine[newLine.lastIndex] = newLine[newLine.lastIndex].removeSuffix("?")
            newLine.add(" ")
            repeat(5) {
                newLine.add("$springNumberString,")
            }
            newLine[newLine.lastIndex] = newLine[newLine.lastIndex].removeSuffix(",")
            newLine.reduce { acc, s -> "$acc$s" }
        }
        return part1(unfoldedInput) // TODO: Out of memory!
    }

    val tinput = readInput("Day12test")
    val input = readInput("Day12")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 21}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 7633}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 525152}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
