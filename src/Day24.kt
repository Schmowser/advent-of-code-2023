import java.math.BigInteger

fun main() {
    fun part1(input: List<String>, min: BigInteger, max: BigInteger): Int {
        val positions = input.map { line ->
            val split = line.split("@")[0]
                .split(",")
                .map { it.replace(" ", "") }
            Vector(split[0].toBigInteger(), split[1].toBigInteger())
        }
        val velocities = input.map { line ->
            val split = line.split("@")[1]
                .split(",")
                .map { it.replace(" ", "") }
            Vector(split[0].toBigInteger(), split[1].toBigInteger())
        }
        println(velocities)

        var result = 0
        for (i in 0..< velocities.size - 1) {
            for (j in i + 1..< velocities.size) {
                println("$i, $j")
                val crossProduct = velocities[i].crossProduct(velocities[j])
                val areVectorsIndependent = crossProduct != BigInteger.ZERO
                if (areVectorsIndependent) {
                    val a = (1 / crossProduct.toDouble())
                        .times(
                            velocities[j].y.toDouble() * (positions[j].x - positions[i].x).toDouble()
                            - velocities[j].x.toDouble() * (positions[j].y - positions[i].y).toDouble()
                        )
                    val b = (1 / crossProduct.toDouble())
                        .times(
                            velocities[i].y.toDouble() * (positions[j].x - positions[i].x).toDouble()
                            - velocities[i].x.toDouble() * (positions[j].y - positions[i].y).toDouble()
                        )

                    val xIntersect = positions[i].x.toDouble() + a.times(velocities[i].x.toDouble())
                    val yIntersect = positions[i].y.toDouble() + a.times(velocities[i].y.toDouble())

                    println("Intersecting ($xIntersect, $yIntersect) with a = $a, b = $b")

                    if (a >= 0 && b > 0 && xIntersect > min.toDouble() && xIntersect < max.toDouble() && yIntersect > min.toDouble() && yIntersect < max.toDouble()) {
                        result++
                    }
                }
            }
        }

        return result
    }


    // x * (-2, 1) - y * (-1, -1) = (18, 19) - (19, 13)
    // (18, 19) + y * (-1, -1) = (b_1, b_2)

    // (19 - 13)

    // 19 + -2x = a | 13 +  1y = b
    // 18 + -2x = a | 19 + -1y = b

    fun part2(input: List<String>): Int {
        return input.size
    }

    val tinput = readInput("Day24test")
    val input = readInput("Day24")

    val testResultPart1 = part1(tinput, 7.toBigInteger(), 27.toBigInteger())
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 2}")
    val resultPart1 = part1(input, 200000000000000.toBigInteger(), 400000000000000.toBigInteger())
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")
//
//    val testResultPart2 = part2(tinput)
//    testResultPart2.println()
//    println("Test 1 Part 2 succeeded: ${testResultPart2 == 2}")
//    val resultPart2 = part2(input)
//    resultPart2.println()
//    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}

class Vector(
    val x: BigInteger, val y: BigInteger
) {
    fun plus(other: Vector): Vector = Vector(
        this.x.plus(other.x), this.y.plus(other.y)
    )

    fun scalar(factor: BigInteger): Vector = Vector(
        this.x.times(factor), this.y.times(factor)
    )

    fun crossProduct(other: Vector): BigInteger =
        this.x.times(other.y).minus(this.y.times(other.x))

    override fun toString(): String = "(${this.x},${this.y})"
}