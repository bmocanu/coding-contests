package main

import (
	"coding-contests/utils"
	"fmt"
)

func main() {
	var numbers [1000]int
	var numbersLen = utils.ReadFileToIntArray("advent-of-code/go/aoc_2019/day01_input.txt", numbers[0:])

	var sum = 0
	for index := 0; index < numbersLen; index++ {
		sum += numbers[index]/3 - 2
	}
	fmt.Printf("Part 1 fuel sum: %d\n", sum)

	sum = 0
	for index := 0; index < numbersLen; index++ {
		sum += day01CalculateFuel(numbers[index])
	}
	fmt.Printf("Part 2 fuel sum: %d\n", sum)
}

func day01CalculateFuel(mass int) int {
	var fuel = mass/3 - 2
	if fuel > 0 {
		return fuel + day01CalculateFuel(fuel)
	} else {
		return 0
	}
}
