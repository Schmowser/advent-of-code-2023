fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val firstDigit = line.first { it.isDigit() }.digitToInt()
            val lastDigit = line.last { it.isDigit() }.digitToInt()
            firstDigit * 10 + lastDigit
        }.reduce { a, b -> a + b }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val sortedNumbers = (
                                line.getWordNumbersWithPosition()
                                + line.getArabicNumbersWithPosition()
                    )
                .sortedBy { it.second }

            val firstDigit = sortedNumbers.firstNotNullOf { it.first }
            val lastDigit = sortedNumbers.reversed().firstNotNullOf { it.first }

            firstDigit * 10 + lastDigit
        }.reduce { a, b -> a + b }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun String.getWordNumbersWithPosition(): List<Pair<Int, Int>> =
    listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
        .filter { this.contains(it.first) }
        .map { listOf(it.second to this.indexOf(it.first), it.second to this.lastIndexOf(it.first)).distinct() }
        .fold(listOf<Pair<Int, Int>>()) { acc, pairs -> acc + pairs }

private fun String.getArabicNumbersWithPosition(): List<Pair<Int, Int>> {
    val firstDigit = this.first { it.isDigit() }.digitToInt()
    val lastDigit = this.reversed().first { it.isDigit() }.digitToInt()
    return listOf(
        firstDigit to this.indexOf(firstDigit.toString()),
        lastDigit to this.length - this.reversed().indexOf(lastDigit.toString()) - 1
    ).distinct()
}