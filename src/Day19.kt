fun main() {
    fun part1(input: List<String>): Int {
        val blankLineIndex = input.indexOf("")

        val rules = input.subList(0, blankLineIndex)
            .associate { ruleLine ->
                val b = ruleLine.split("{")

                b[0] to { inputMap: Map<String, Int> ->
                    val rules = b[1].removeSuffix("}").split(",")
                    var result = rules.last()
                    run breaking@ {
                        rules.forEach {
                            if (it.contains(":")) {
                                if (it.contains("<")) {
                                    if (inputMap[it.substringBefore("<")]!! < it.substringAfter("<").substringBefore(":").toInt()) {
                                        result = it.substringAfter(":")
                                        return@breaking
                                    }
                                } else {
                                    if (inputMap[it.substringBefore(">")]!! > it.substringAfter(">").substringBefore(":").toInt()) {
                                        result = it.substringAfter(":")
                                        return@breaking
                                    }
                                }
                            }
                        }
                    }
                    result
                }
            }

        val parts = input.subList(blankLineIndex + 1, input.lastIndex + 1)
            .map { partLine ->
                val a = partLine.removePrefix("{")
                    .removeSuffix("}")
                    .split(",")
                    .map { it.substringAfter("=").toInt() }
                val tempMap = mutableMapOf<String, Int>()
                tempMap["x"] = a[0]
                tempMap["m"] = a[1]
                tempMap["a"] = a[2]
                tempMap["s"] = a[3]
                tempMap
            }

        var result = 0
        parts.forEach {
            print("Part $it ")
            var tempRule = "in"
            while (tempRule.length > 1) {
                tempRule = rules[tempRule]!!.invoke(it)
                print(" $tempRule |")
            }
            println("")
            if (tempRule == "A") {
                result += it["x"]!! + it["m"]!! + it["a"]!! + it["s"]!!
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day19test")
    val input = readInput("Day19")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 19114}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 398527}")

//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}