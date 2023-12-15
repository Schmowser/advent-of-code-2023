fun main() {
    fun part1(input: List<String>): Int {
        return input[0].split(",").sumOf { individualStep ->
            getHASH(individualStep)
        }
    }

    fun part2(input: List<String>): Int {
        val boxToLensListMap = emptyMap<Int, MutableList<Pair<String, Int>>>().toMutableMap()
        for (i in 0 until 256) {
            boxToLensListMap[i] = mutableListOf()
        }
        input[0].split(",").map { individualStep ->

            if (individualStep.contains("-")) {
                val label = individualStep.removeSuffix("-")
                val correspondingBox = getHASH(label)

                boxToLensListMap[correspondingBox]?.removeIf { it.first == label }
            } else {
                val splitStep = individualStep.split("=")
                val labelToFocalLength = splitStep[0] to splitStep[1].toInt()
                val correspondingBox = getHASH(labelToFocalLength.first)

                val indexOfLabelFocalLenghPair = boxToLensListMap[correspondingBox]
                    ?.indexOfFirst { it.first == labelToFocalLength.first }
                if (indexOfLabelFocalLenghPair == -1) {
                    boxToLensListMap[correspondingBox]?.add(labelToFocalLength)
                } else {
                    boxToLensListMap[correspondingBox]?.set(indexOfLabelFocalLenghPair!!, labelToFocalLength)
                }
            }
        }
        println("Final Box content: $boxToLensListMap")

        return boxToLensListMap.map { box ->
            if (box.value.isNotEmpty()) {
                (box.key + 1) * box.value.mapIndexed { index, pair ->
                    (index + 1) * pair.second
                }.reduce { acc, unit -> acc + unit }
            } else 0
        }.reduce { acc, unit -> acc + unit }
    }

    val tinput = readInput("Day15test")
    val input = readInput("Day15")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 1320}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 513214}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 145}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 258826}")
}

private fun getHASH(input: String): Int {
    var currentValue = 0
    input.forEach {
        currentValue += it.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}