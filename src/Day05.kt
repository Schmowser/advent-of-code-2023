import kotlin.collections.minOfOrNull as kotlinCollectionsMinOfOrNull

fun main() {
    fun part1(input: List<String>): Int {
        val seeds = input[0]
            .removePrefix("seeds: ")
            .split(" ")
            .map { it.toInt() }

        val mapList = mutableListOf<Map<Int, Int>>()

        input.drop(2).forEach { line ->
            var map1 = (1 until 99).associateWith { it }.toMutableMap()
            if (!(line == "" || line.contains("map"))) {
                map1 = map1 // TODO
            } else {
                mapList.add(map1)
                map1 = (1 until 99).associateWith { it }.toMutableMap()
            }
        }

        println(seeds)
        println(mapList)
        return seeds.kotlinCollectionsMinOfOrNull { seed ->
            var location = seed
            mapList.forEach {
                location = it[location] ?: 1
            }
            location
        } ?: 1
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day05test")
    val input = readInput("Day05")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 35}")
    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 0}")

}
