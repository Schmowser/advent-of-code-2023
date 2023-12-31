fun main() {
    fun part1(input: List<String>): Int {
        println(input)
        val sortedHands = input.sortedBy {
            it.split(" ").first().toHand()
        }
        println(sortedHands)
        return sortedHands.mapIndexed { index, s ->
            s.split(" ")[1].toInt() * (index + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        println(input)
        val sortedHands = input.sortedBy {
            it.split(" ").first().toHandJoker()
        }
        println(sortedHands)
        return sortedHands.mapIndexed { index, s ->
            s.split(" ")[1].toInt() * (index + 1)
        }.sum()
    }

    val tinput = readInput("Day07test")
    val input = readInput("Day07")

    val testResultPart1 = part1(tinput)
    testResultPart1.println()
    println("Test 1 Part 1 succeeded: ${testResultPart1 == 6440}")
    val resultPart1 = part1(input)
    resultPart1.println()
    println("Part 1 succeeded: ${resultPart1 == 114400}")

    val testResultPart2 = part2(tinput)
    testResultPart2.println()
    println("Test 1 Part 2 succeeded: ${testResultPart2 == 5905}")
    val resultPart2 = part2(input)
    resultPart2.println()
    println("Part 2 succeeded: ${resultPart2 == 250825971}")
}

enum class Card(
    val value: Int,
) {
    A(14),
    K(13),
    Q(12),
    J(11),
    T(10),
    `9`(9),
    `8`(8),
    `7`(7),
    `6`(6),
    `5`(5),
    `4`(4),
    `3`(3),
    `2`(2);
}

enum class CardWithJokerRule(
    val value: Int,
) {
    A(14),
    K(13),
    Q(12),
    J(0),
    T(10),
    `9`(9),
    `8`(8),
    `7`(7),
    `6`(6),
    `5`(5),
    `4`(4),
    `3`(3),
    `2`(2);
}

enum class HandType(
    val value: Int,
) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0),
}

class Hand(
    private val content: List<Card>,
): Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        val handTypeComparison = this.handType.value - other.handType.value
        return if (handTypeComparison == 0) {
            this.content.mapIndexed { index, card ->
                val cardComparison = card.value - other.content[index].value
                if (cardComparison != 0) {
                    return cardComparison
                }
            }
            0
        } else handTypeComparison
    }

    override fun equals(other: Any?): Boolean {
        return this.handType == (other as? Hand)?.handType
    }

    private val handType: HandType
        get() {
            val cardAmountMap = mutableMapOf<Card, Int>()
            this.content.forEach {
                cardAmountMap[it] = (cardAmountMap[it] ?: 0) + 1
            }
            return when (cardAmountMap.values.max()) {
                5 -> HandType.FIVE_OF_A_KIND
                4 -> HandType.FOUR_OF_A_KIND
                3 -> if (cardAmountMap.values.contains(2)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
                2 -> if (cardAmountMap.values.size == 3) HandType.TWO_PAIR else HandType.ONE_PAIR
                1 -> HandType.HIGH_CARD
                else -> HandType.HIGH_CARD
            }
        }
}

class HandWithJokerRule(
    private val content: List<CardWithJokerRule>,
): Comparable<HandWithJokerRule> {
    override fun compareTo(other: HandWithJokerRule): Int {
        val handTypeComparison = this.handType.value - other.handType.value
        return if (handTypeComparison == 0) {
            this.content.mapIndexed { index, card ->
                val cardComparison = card.value - other.content[index].value
                if (cardComparison != 0) {
                    return cardComparison
                }
            }
            0
        } else handTypeComparison
    }

override fun equals(other: Any?): Boolean {
    return this.handType == (other as? HandWithJokerRule)?.handType
}

private val handType: HandType
    get() {
        val cardAmountMap = mutableMapOf<CardWithJokerRule, Int>()
        this.content.forEach {
            cardAmountMap[it] = (cardAmountMap[it] ?: 0) + 1
        }
        if (cardAmountMap.keys.contains(CardWithJokerRule.J)) {
            val numberOfJokers = cardAmountMap[CardWithJokerRule.J] ?: 0
            cardAmountMap.remove(CardWithJokerRule.J)
            val cardMax = cardAmountMap.maxByOrNull { it.value }
            cardMax?.let {
                cardAmountMap[it.key] = it.value + numberOfJokers
            } ?: return HandType.FIVE_OF_A_KIND
        }
        return when (cardAmountMap.values.max()) {
            5 -> HandType.FIVE_OF_A_KIND
            4 -> HandType.FOUR_OF_A_KIND
            3 -> if (cardAmountMap.values.contains(2)) HandType.FULL_HOUSE else HandType.THREE_OF_A_KIND
            2 -> if (cardAmountMap.values.size == 3) HandType.TWO_PAIR else HandType.ONE_PAIR
            1 -> HandType.HIGH_CARD
            else -> HandType.HIGH_CARD
        }
    }
}

fun String.toHand(): Hand = Hand(
    this.map { Card.valueOf(it.toString()) }
)

fun String.toHandJoker(): HandWithJokerRule = HandWithJokerRule(
    this.map { CardWithJokerRule.valueOf(it.toString()) }
)