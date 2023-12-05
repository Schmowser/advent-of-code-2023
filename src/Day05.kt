import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): String {
        val seeds = input[0]
            .removePrefix("seeds: ")
            .split(" ")
            .map { it.toBigInteger() }

        return seeds.map { seed ->
            var resultSeed = seed
            var skip = false
            input.drop(2).filter { line -> line != "" }.forEach { line ->
                if (!line.contains("map")) {
                    if (skip == false) {
                        line.split(" ").let {
                            val diff = resultSeed.minus(it[1].toBigInteger())
                            if (diff >= BigInteger.ZERO && diff <= it[2].toBigInteger()) {
                                resultSeed = diff.plus(it[0].toBigInteger())
                                skip = true
                            }
                        }
                    }
                } else {
                    skip = false
                }
            }
            resultSeed
        }.min().toString()
    }

    fun part2(input: List<String>): String {
        val seedPairs = input[0]
            .removePrefix("seeds: ")
            .split(" ")
            .map { it.toBigInteger() }

        val reversedMaps = input.drop(2).filter { line -> line != "" }.reversed()
        var currentMin = BigInteger.valueOf(25_000_000L)
        var candidateSeed = BigInteger.ZERO
        var skip = false
        while (!candidateSeed.isInSeedRanges(seedPairs)) {
            println(currentMin)
            candidateSeed = currentMin
            reversedMaps.forEach { line ->
                if (!line.contains("map")) {
                    if (!skip) {
                        line.split(" ").let {
                            val diff = candidateSeed.minus(it[0].toBigInteger())
                            if (diff >= BigInteger.ZERO && diff <= it[2].toBigInteger()) {
                                candidateSeed = diff.plus(it[1].toBigInteger())
                                skip = true
                            }
                        }
                    }
                } else {
                    skip = false
                }
            }
            currentMin += BigInteger.ONE
        }

        return (currentMin - BigInteger.ONE).toString()
    }

    val tinput = readInput("Day05test")
    val input = readInput("Day05")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == "35"}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == "175622908"}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == "46"}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == "?"}")
    // 175622908 too high!
    // 100000008 too high!
    // 50_000_000 too high!
    // 25_000_000
    // 13_000_000L too low
}

fun BigInteger.isInSeedRanges(seedRanges: List<BigInteger>): Boolean {
    for (i in seedRanges.indices step 2) {
        if (this >= seedRanges[i] && this <= seedRanges[i] + seedRanges[i + 1]) {
            return true
        }
    }
    return false
}