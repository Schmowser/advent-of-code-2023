fun main() {
    fun part1(input: List<String>): Int {
        val sortedInput = input.map { line ->
            val firstSecond = line.split("~")
            val first = firstSecond[0].split(",")
            val second = firstSecond[1].split(",")
            Triple(first[0].toInt()..second[0].toInt(), first[1].toInt()..second[1].toInt(), first[2].toInt()..second[2].toInt())
        }.sortedBy { it.third.min() }
        println(sortedInput)

        val fallenDownBricks = mutableListOf<Triple<IntRange, IntRange, IntRange>>()
        fallenDownBricks.add(sortedInput[0])
        sortedInput.takeLast(sortedInput.size - 1).forEach { brick ->
            val highestSupportList = fallenDownBricks.filter {
                it.first.intersect(brick.first).isNotEmpty() && it.second.intersect(brick.second).isNotEmpty()
            }

            if (highestSupportList.isNotEmpty()) {
                val highestSupport = highestSupportList.maxBy { it.third.last }
                fallenDownBricks.add(
                    Triple(
                        brick.first,
                        brick.second,
                        (highestSupport.third.last + 1)..(highestSupport.third.last + 1 + (brick.third.last - brick.third.first))
                    )
                )
            } else {
                fallenDownBricks.add(
                    Triple(brick.first, brick.second, 1..(1 + (brick.third.last - brick.third.first)))
                )
            }

            // fallenDownBricks.sortBy { it.third.last }
        }
        fallenDownBricks.println()

        val brickIndexToSupportedByIndex =
                fallenDownBricks.associateWith { mutableSetOf<Triple<IntRange, IntRange, IntRange>>() }
        for (bottomLevel in 1..fallenDownBricks.maxOf { it.third.first }) {
            fallenDownBricks.filter { it.third.last == bottomLevel - 1 }.forEach { lowerBrick ->
                fallenDownBricks.filter { it.third.first == bottomLevel }.forEach { upperBrick ->
                    if (lowerBrick.first.intersect(upperBrick.first).isNotEmpty() && lowerBrick.second.intersect(upperBrick.second).isNotEmpty()) {
                        brickIndexToSupportedByIndex[upperBrick]?.add(lowerBrick)
                    }
                }
            }
        }
        brickIndexToSupportedByIndex.println()

        val unremovableBricks = fallenDownBricks.filter { brick ->
            brickIndexToSupportedByIndex.values.filter { supports -> supports.contains(brick) }
                .any { it.size == 1 }
        }
        //unremovableBricks.println()

        input.size.println()
        brickIndexToSupportedByIndex.filter { it.value.size == 1 }.values.distinct().size.println()

        return fallenDownBricks.size - unremovableBricks.size
    }

    fun part2(input: List<String>): Int {
        val sortedInput = input.map { line ->
            val firstSecond = line.split("~")
            val first = firstSecond[0].split(",")
            val second = firstSecond[1].split(",")
            Triple(first[0].toInt()..second[0].toInt(), first[1].toInt()..second[1].toInt(), first[2].toInt()..second[2].toInt())
        }.sortedBy { it.third.min() }
        println(sortedInput)

        val fallenDownBricks = mutableListOf<Triple<IntRange, IntRange, IntRange>>()
        fallenDownBricks.add(sortedInput[0])
        sortedInput.takeLast(sortedInput.size - 1).forEach { brick ->
            val highestSupportList = fallenDownBricks.filter {
                it.first.intersect(brick.first).isNotEmpty() && it.second.intersect(brick.second).isNotEmpty()
            }

            if (highestSupportList.isNotEmpty()) {
                val highestSupport = highestSupportList.maxBy { it.third.last }
                fallenDownBricks.add(
                    Triple(
                        brick.first,
                        brick.second,
                        (highestSupport.third.last + 1)..(highestSupport.third.last + 1 + (brick.third.last - brick.third.first))
                    )
                )
            } else {
                fallenDownBricks.add(
                    Triple(brick.first, brick.second, 1..(1 + (brick.third.last - brick.third.first)))
                )
            }

        // fallenDownBricks.sortBy { it.third.last }
        }
        fallenDownBricks.println()

        val brickIndexToSupportedByIndex =
            fallenDownBricks.associateWith { mutableSetOf<Triple<IntRange, IntRange, IntRange>>() }
        for (bottomLevel in 1..fallenDownBricks.maxOf { it.third.first }) {
            fallenDownBricks.filter { it.third.last == bottomLevel - 1 }.forEach { lowerBrick ->
                fallenDownBricks.filter { it.third.first == bottomLevel }.forEach { upperBrick ->
                    if (lowerBrick.first.intersect(upperBrick.first).isNotEmpty() && lowerBrick.second.intersect(upperBrick.second).isNotEmpty()) {
                        brickIndexToSupportedByIndex[upperBrick]?.add(lowerBrick)
                    }
                }
            }
        }
        brickIndexToSupportedByIndex.println()

        return fallenDownBricks.map { brick ->
            val bricksInChainReaction = mutableSetOf(brick)
            val currentBricks = mutableSetOf(brick)
            while (currentBricks.isNotEmpty()) {
                val currentBrick = currentBricks.first()
                val bricksToDisintegrate = brickIndexToSupportedByIndex.filter {
                    it.value.contains(currentBrick) && it.value.minus(bricksInChainReaction).isEmpty()
                }.keys
                currentBricks.addAll(bricksToDisintegrate)
                bricksInChainReaction.addAll(bricksToDisintegrate)

                currentBricks.remove(currentBrick)
            }
            (bricksInChainReaction.size - 1).also { it.println() }
         }
            .reduce { acc, i -> acc.plus(i) }
    }

    val tinput = readInput("Day22test1")
    val input = readInput("Day22")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 5}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 393}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 7}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}
