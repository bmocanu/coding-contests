package main

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
)

func main_day01() {
	var numbers [1000]int
	var numbersLen, err = utilities.ReadFileToIntArray("aoc-2019/day01_input.txt", numbers[0:])
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return
	}

	var sum = 0
	for index := 0; index < numbersLen; index++ {
		sum += numbers[index]/3 - 2
	}
	fmt.Printf("Part 1 fuel sum: %d\n", sum)

	sum = 0
	for index := 0; index < numbersLen; index++ {
		sum += day01_calculateFuel(numbers[index])
	}
	fmt.Printf("Part 2 fuel sum: %d\n", sum)
}

func day01_calculateFuel(mass int) int {
	var fuel = mass/3 - 2
	if fuel > 0 {
		return fuel + day01_calculateFuel(fuel)
	} else {
		return 0
	}
}
