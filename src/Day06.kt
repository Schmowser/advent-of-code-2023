import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].split(" ").drop(1).filter { it != "" }.map { it.toInt() }
        val distances = input[1].split(" ").drop(1).filter { it != "" }.map { it.toInt() }
        times.println()
        distances.println()

        return times.mapIndexed { index, time ->
            var numberOfWaysToBeatTheRecord = 0
            val distance = distances[index]
            for (i in 1 until distance) {
                if (i * (time - i) > distance) {
                    numberOfWaysToBeatTheRecord++
                }
                if (i * (time - i) < 0) {
                    break
                }
            }
            numberOfWaysToBeatTheRecord
        }.fold(1) { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        //val time = 15
        //val distance = 40

        // TODO: Use BigInteger
        val time = input[0].removePrefix("Time:      ").replace(" ", "").toInt()
        val distance = input[1].removePrefix("Distance:  ").replace(" ", "").toInt()
        time.println()
        distance.println()

        // Find zeroes of f(i) = i * (time - i) - distance
        // Use f'(i) = time - 2i

        val function = { i: Double -> i * (time - i) - distance }
        val derivedFunction = { i: Double -> time - 2 * i }


        var firstZero = round( time * 0.0)
        var oldFirstZero = firstZero
        var lastZero = 71520.0
        var oldLastZero = lastZero

        do {
            oldFirstZero = firstZero
            firstZero = oldFirstZero - function(oldFirstZero) / derivedFunction(oldFirstZero)
            println("old: $oldFirstZero, new: $firstZero")
        } while (Math.abs(firstZero - oldFirstZero) > 0.5)
        println("FIRSTZERO = $firstZero")

        do {
            oldLastZero = lastZero
            lastZero = oldLastZero - function(oldLastZero) / derivedFunction(firstZero)
            println("old: $oldLastZero, new: $lastZero")
        } while (Math.abs(lastZero - oldLastZero) > 0.5)
        println("LASTZERO = $lastZero")

        return floor(lastZero).toInt() - ceil(firstZero).toInt() - 1
    }

    val tinput = readInput("Day06test")
    val input = readInput("Day06")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 288}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 71503}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 0}")
}