package main

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
)

func main_day02() {
	var fileAsString, err = utilities.ReadFileToString("aoc-2019/day02_input.txt")
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return
	}

	var memory = make([]int, 1000)
	utilities.SplitCsvStringToIntArray(fileAsString, memory)

	memory[1] = 12
	memory[2] = 2

	fmt.Printf("Probl 1 result: %d\n", day02RunIntCode(memory))

	for noun := 0; noun <= 99; noun++ {
		for verb := 0; verb <= 99; verb++ {
			memory[1] = noun
			memory[2] = verb
			var result = day02RunIntCode(memory)
			if result == 19690720 {
				fmt.Printf("Probl 2 result: %d\n", 100*noun+verb)
			}
		}
	}
}

func day02RunIntCode(originalMemory []int) int {
	var memory = make([]int, 1000)
	copy(memory, originalMemory)

	var currentPos = 0
	for memory[currentPos] != 99 {
		var opCode = memory[currentPos]
		var firstParameterAddr = memory[currentPos+1]
		var secondParameterAddr = memory[currentPos+2]
		var destinationAddr = memory[currentPos+3]
		var result int
		switch opCode {
		case 1:
			result = memory[firstParameterAddr] + memory[secondParameterAddr]
		case 2:
			result = memory[firstParameterAddr] * memory[secondParameterAddr]
		default:
			fmt.Printf("Invalid opCode found: [%d]\n", opCode)
		}

		memory[destinationAddr] = result
		currentPos += 4
	}

	return memory[0]
}
