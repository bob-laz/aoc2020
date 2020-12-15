import Day11.Seat
import Day11.Seats
import util.readFile

val input = readFile("day11.txt")

typealias Seats = List<String>
typealias Seat = Pair<Int, Int>

// @formatter:off
private val neighbors = sequenceOf(
    -1 to -1, -1 to 0, -1 to 1,
    0 to -1,           0 to 1,
    1 to -1,  1 to 0,  1 to 1
)
// @formatter:on

operator fun Seats.contains(seat: Seat): Boolean =
    seat.first in this.indices && seat.second in this.first().indices

operator fun Seat.plus(that: Seat): Seat =
    Seat(this.first + that.first, this.second + that.second)

fun Seats.occupied(): Int =
    this.sumBy { it.count { row -> row == '#' } }

fun Seats.print() = this.forEach { println(it) }

fun countOccupiedNeighbors(seats: Seats, seat: Seat): Int =
    neighbors
        .map { it + seat }
        .filter { it in seats }
        .count { seats[it.first][it.second] == '#' }

fun generateNextSeats(seats: Seats, tolerance: Int, countFunction: (Seats, Seat) -> Int): List<String> {
    return seats.mapIndexed { x, row ->
        row.mapIndexed { y, char ->
            val occupiedNeighbors = countFunction(seats, Seat(x, y))
            when {
                (char == 'L' && occupiedNeighbors == 0) -> '#'
                (char == '#' && occupiedNeighbors >= tolerance) -> 'L'
                else -> char
            }
        }.joinToString("")
    }.toList()
}

fun findSeatOnVector(seats: Seats, seat: Seat, vector: Seat): Char? =
    generateSequence(seat + vector) { it + vector }
        .map { if (it in seats) seats[it.first][it.second] else null }
        .first { it == null || it != '.' }

fun countFarNeighbors(seats: Seats, seat: Seat): Int =
    neighbors
        .mapNotNull { findSeatOnVector(seats, seat, it) }
        .count { it == '#' }

fun part1(): Int =
    generateSequence(input) { generateNextSeats(it, 4, this::countOccupiedNeighbors) }
        .zipWithNext()
        .first { it.first == it.second }
        .first
        .occupied()

fun part2(): Int = generateSequence(input) { generateNextSeats(it, 5, this::countFarNeighbors) }
    .zipWithNext()
    .first { it.first == it.second }
    .first
    .occupied()

println("Part 1: ${part1()}")
println("Part 2: ${part2()}")
