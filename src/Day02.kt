fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val gameId = line.substringBefore(":").removePrefix("Game ").toInt()
            val isGamePossible = line.substringAfter(": ").split("; ")
                .all { it.containsLessBallsThenMax() }
            gameId.takeIf { isGamePossible } ?: 0
        }.reduce { acc, i -> acc + i }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val colorMaxMap = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
            line.substringAfter(": ").split("; ")
                .forEach { set ->
                    set.split(", ")
                        .map { it.substringBefore(" ").toInt() to it.substringAfter(" ") }
                        .forEach { if (it.first > (colorMaxMap[it.second] ?: Int.MAX_VALUE)) colorMaxMap[it.second] = it.first }
                }
            colorMaxMap.values.reduce { acc, i -> acc * i }
        }.reduce { acc, i -> acc + i }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun String.containsLessBallsThenMax(): Boolean {
    val colorMaxMap = mapOf("red" to 12, "green" to 13, "blue" to 14)
    return this.split(", ").all {
        val amountOfBalls = it.substringBefore(" ").toInt()
        val ballColor = it.substringAfter(" ")
        amountOfBalls <= (colorMaxMap[ballColor] ?: -Int.MAX_VALUE)
    }
}