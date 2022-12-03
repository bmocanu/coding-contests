package main

import (
	"../utils"
	"fmt"
)

// Probl 1 result: 4570637
// Probl 2 result: 5485

func main02() {
	var fileAsString = utils.ReadFileToString("aoc_2019/day02_input.txt")
	var memory = utils.SplitCsvStringToFlexibleArray(fileAsString)

	memory.Set(1, 12)
	memory.Set(2, 2)

	fmt.Printf("Probl 1 result: %d\n", day02RunIntCode(memory.DeepClone()))

	for noun := 0; noun <= 99; noun++ {
		for verb := 0; verb <= 99; verb++ {
			memory.Set(1, noun)
			memory.Set(2, verb)
			var result = day02RunIntCode(memory.DeepClone())
			if result == 19690720 {
				fmt.Printf("Probl 2 result: %d\n", 100*noun+verb)
			}
		}
	}

	memory.Print()
}

func day02RunIntCode(memory *utils.FlexArray) int {
	var currentPos = 0
	for memory.Get(currentPos) != 99 {
		var opCode = memory.Get(currentPos)
		var firstParameterAddr = memory.Get(currentPos + 1)
		var secondParameterAddr = memory.Get(currentPos + 2)
		var destinationAddr = memory.Get(currentPos + 3)
		var result int
		switch opCode {
		case 1:
			result = memory.Get(firstParameterAddr) + memory.Get(secondParameterAddr)
		case 2:
			result = memory.Get(firstParameterAddr) * memory.Get(secondParameterAddr)
		default:
			fmt.Printf("Invalid opCode found: [%d]\n", opCode)
		}

		memory.Set(destinationAddr, result)
		currentPos += 4
	}

	return memory.Get(0)
}
