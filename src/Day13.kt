fun main() {
    fun part1(input: List<String>): Int {
        val patternList = mutableListOf<List<String>>()
        var tempPattern = mutableListOf<String>()
        input.map {
            if (it != "") {
                tempPattern.add(it)
            } else {
                patternList.add(tempPattern)
                tempPattern = mutableListOf()
            }
        }
        patternList.add(tempPattern)
        println("${patternList.size} Patterns")

        return patternList.map { pattern ->
            val potentialHorizontalMirror = mutableListOf<Int>()
            val line1 = pattern[0]

            for (i in 1 until line1.length / 2 + 1) {
                var substringToCheck = line1.substring(0, 2 * i)
                if (substringToCheck == substringToCheck.reversed()) {
                    potentialHorizontalMirror.add(i)
                }
                substringToCheck = line1.substring(line1.length - 2 * i, line1.length)
                //println("Hor Substring: $substringToCheck for ${line1.length - 2 * i}")
                if (substringToCheck == substringToCheck.reversed()) {
                    potentialHorizontalMirror.add(line1.length - i)
                }
            }

            val invalidMirrors = mutableSetOf<Int>()
            pattern.map {
                for (mirror in potentialHorizontalMirror) {
                    val substringToCheck = if (mirror < it.length / 2 + 1) {
                        it.substring(0, 2 * mirror)
                    } else {
                        it.substring(mirror - (it.length - mirror), it.length)
                    }
                    if (substringToCheck != substringToCheck.reversed()) {
                        invalidMirrors.add(mirror)
                    }
                }
            }

            if (potentialHorizontalMirror.minus(invalidMirrors).isEmpty()) {
                var verticalMirror = 0
                loop@ for (i in 1 until pattern.size) {
                    var upperSide = i - 1
                    var lowerSide = i
                    while (upperSide >= 0 && lowerSide < pattern.size) {
                        //println("${pattern[upperSide]} equals ${pattern[lowerSide]}?")
                        if (pattern[upperSide] != pattern[lowerSide]) {
                            continue@loop
                        }
                        upperSide--
                        lowerSide++
                    }
                    verticalMirror = i
                }
                println("Vertical Mirror: ${100 * verticalMirror}")
                100 * verticalMirror
            } else {
                println("Horizontal Mirror: ${potentialHorizontalMirror.minus(invalidMirrors).first()}")
                potentialHorizontalMirror.minus(invalidMirrors).first()
            }
        }.reduce { acc, unit -> acc + unit }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day13test")
    val tinput2 = readInput("Day13test2")
    val input = readInput("Day13")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 405}")
    val testResult2Part1 = part1(tinput2)
    testResult2Part1.println()
    println("Test 2 Part 1 succeeded: ${testResult2Part1 == 8}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 0}")

//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
