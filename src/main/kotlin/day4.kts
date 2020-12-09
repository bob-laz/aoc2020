import util.readFile

val input = readFile("day4.txt")

fun parseInput(validationFunc: (String, String) -> Boolean): List<Map<String, String>> {
    val passportList = mutableListOf<Map<String, String>>()
    val tempMap = mutableMapOf<String, String>()
    for (line in input) {
        if (line.isBlank()) {
            passportList.add(tempMap.toMap())
            tempMap.clear()
        } else {
            line.split(" ").forEach {
                val keyVal = it.split(":")
                if (validationFunc(keyVal[0], keyVal[1])) tempMap[keyVal[0]] = keyVal[1]
            }
        }
    }
    // reaches end of file and doesn't add last item, so add one more
    passportList.add(tempMap)
    return passportList
}

var validateField = { key: String, value: String ->
    when (key) {
        "byr" -> value.toIntOrNull() in 1920..2002
        "iyr" -> value.toIntOrNull() in 2010..2020
        "eyr" -> value.toIntOrNull() in 2020..2030
        "hgt" -> (value.endsWith("cm") && value.removeSuffix("cm")
            .toIntOrNull() in 150..193) || (value.endsWith("in") && value.removeSuffix("in").toIntOrNull() in 59..76)
        "hcl" -> value.matches(Regex("#[a-f0-9]{6}"))
        "ecl" -> value.matches(Regex("(amb|blu|brn|gry|grn|hzl|oth)"))
        "pid" -> value.matches(Regex("\\d{9}"))
        else -> false
    }
}

fun filterList(passportList: List<Map<String, String>>) =
    passportList.filter {
        it.containsKey("byr") && it.containsKey("iyr") && it.containsKey("eyr") && it.containsKey("hgt") && it.containsKey(
            "hcl"
        ) && it.containsKey("ecl") && it.containsKey("pid")
    }.size

fun part1() {
    println("Part 1: ${filterList(parseInput { _, _ -> true })}")
}

fun part2() {
    println("Part 2: ${filterList(parseInput(validateField))}")
}

part1()
part2()
