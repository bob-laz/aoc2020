import util.readFile

// read input from file, list of strings is already a 2d array since strings are character arrays themselves
// iterate over lines in file checking if value at (x,y) is #, increment counter if true
// if x moves past the end of line, use modulo to wrap back around
val input = readFile("day3.txt")

fun solveDay3(xStep: Int, yStep: Int): Long {
    var x = 0
    var y = 0
    var treeCount = 0
    while (y < input.size) {
        if (input[y][x] == '#') treeCount++
        x = (x + xStep) % input[0].length
        y += yStep
    }
    return treeCount.toLong()
}

// need long or else int multiplication will result in overflow
fun part2(): Long {
    return solveDay3(1, 1) * solveDay3(3, 1) * solveDay3(5, 1) * solveDay3(7, 1) * solveDay3(1, 2)
}

println("Part 1: ${solveDay3(3, 1)}")
println("Part 2: ${part2()}")
