import java.math.BigInteger
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val leftRightInstruction = input[0]
        // TODO: Use .associateByTo()?
        val lookUpMap = input.drop(2).associate { line ->
            val splitLine = line.split(" = ")
            splitLine[0] to (splitLine[1].removePrefix("(").removeSuffix(")").split(", "))
        }

        var tempLocation = "AAA"
        tempLocation.println()
        var steps = 0
        while (tempLocation != "ZZZ") {
            val currentInstruction = leftRightInstruction[steps % leftRightInstruction.length]
            tempLocation = if (currentInstruction == 'L') lookUpMap[tempLocation]!![0] else lookUpMap[tempLocation]!![1]
            steps++
        }

        return steps
    }

    fun part2(input: List<String>): BigInteger {
        val leftRightInstruction = input[0]
        val lookUpMap = input.drop(2).associate { line ->
            val splitLine = line.split(" = ")
            splitLine[0] to (splitLine[1].removePrefix("(").removeSuffix(")").split(", "))
        }

        var tempLocations = lookUpMap.keys.filter { it.endsWith("A") }
        println("Start locations: $tempLocations")
        return tempLocations.map { location ->
            var tempLocation = location
            var repetition = mutableListOf<String>()
            var steps = 0

            while (!tempLocation.endsWith("Z")) {
                repetition.add(tempLocation)
                val currentInstruction = leftRightInstruction[steps % leftRightInstruction.length]
                tempLocation = if (currentInstruction == 'L') lookUpMap[tempLocation]!![0] else lookUpMap[tempLocation]!![1]
                steps++
            }
            repetition.add(tempLocation)

            val repetition1 = repetition
            repetition = mutableListOf<String>()

            val tempCurrentInstruction = leftRightInstruction[steps % leftRightInstruction.length]
            tempLocation = if (tempCurrentInstruction == 'L') lookUpMap[tempLocation]!![0] else lookUpMap[tempLocation]!![1]
            steps++

            while (!tempLocation.endsWith("Z")) {
                repetition.add(tempLocation)
                val currentInstruction = leftRightInstruction[steps % leftRightInstruction.length]
                tempLocation = if (currentInstruction == 'L') lookUpMap[tempLocation]!![0] else lookUpMap[tempLocation]!![1]
                steps++
            }
            repetition.add(tempLocation)

            val repetition2 = repetition

            repetition.size.toBigInteger()
        }.fold(BigInteger.ONE) { acc, i ->
            leastCommonMultiple(acc, i)
        }
    }

    val tinput = readInput("Day08test")
    val tinput2 = readInput("Day08test2")
    val tinput3 = readInput("Day08test3")
    val input = readInput("Day08")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 2}")
    val testResult2Part1 = part1(tinput2)
    testResult2Part1.println()
    println("Test 2 Part 1 succeeded: ${testResult2Part1 == 6}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 20777}")

    val testResultPart2 = part2(tinput3)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2.toString() == "6"}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2.toString() == "13289612809129"}")
}

fun leastCommonMultiple(x: BigInteger, y: BigInteger): BigInteger {
    val larger = if (x > y) x else y
    val maxLCM = x * y
    var tempLCM = larger
    while (tempLCM <= maxLCM) {
        if (tempLCM.mod(x) == BigInteger.ZERO && tempLCM.mod(y) == BigInteger.ZERO) {
            return tempLCM
        }
        tempLCM += larger
    }
    return maxLCM
}