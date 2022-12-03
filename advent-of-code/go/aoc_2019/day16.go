package main

import (
	"../utils"
	"fmt"
)

func main16() {
	var digits = utils.ReadFileToDigitsArray("aoc_2019/day16_input_mine.txt")
	//var digits = utils.ReadFileToDigitsArray("aoc_2019/day16_input_mine.txt")
	utils.PrintArray(processDigits(digits), 1)

	fmt.Scanf("%s")

	for round := 0; round < 100; round++ {
		for elem := 0; elem < 100; elem++ {
			fmt.Printf("%2d ", patternDigit(round, elem))
		}
		fmt.Println()
	}

	// ten thousands digits
	//fmt.Println("Producing 10000 x initial input...")
	//var digitCount = len(digits)
	//var ttDigits = make([]int, digitCount*10000)
	//for index := 0; index < digitCount*10000; index++ {
	//	ttDigits[index] = digits[index%digitCount]
	//}
	//
	//fmt.Println("Processing this shitload of digits...")
	//var part2Result = processDigits(ttDigits)
	//fmt.Println("Calculating message offset...")
	//var messageOffset = utils.ArrayToNumber(digits[:7])
	//fmt.Print("Part 2 result: ")
	//fmt.Println("Printing the message...")
	//utils.PrintArray(part2Result[messageOffset:messageOffset+8], 1)

	//5976809
}

func processDigits(digits []int) []int {
	var digitCount = len(digits)
	var newDigits = make([]int, digitCount)
	for phaseIndex := 0; phaseIndex < 100; phaseIndex++ {
		for round := 0; round < digitCount; round++ {
			fmt.Printf("Processing phase-round %d-%d ...\n", phaseIndex, round)
			var newElem = 0
			var cursor = -1
			var index = 0
			for cursor < digitCount {
				// skip first round elements
				cursor += round + 1
				// add the next round+1 elements
				for index = 0; index <= round && cursor < digitCount; index++ {
					newElem += digits[cursor]
					cursor++
				}
				// skip the next round+1 elements
				cursor += round + 1
				// subtract the next round+1 elements
				for index = 0; index <= round && cursor < digitCount; index++ {
					newElem -= digits[cursor]
					cursor++
				}
			}
			newDigits[round] = utils.Abs(newElem) % 10
		}
		digits = newDigits
	}
	return digits
}

func patternDigit(round int, elem int) int {
	var basePattern = [...]int{0, 1, 0, -1}
	var index = 0
	for globalIndex, roundIndex := 1, 0; globalIndex <= elem+1; {
		roundIndex++
		if roundIndex >= round+1 {
			roundIndex = 0
			index = (index + 1) % len(basePattern)
		}
		globalIndex++
	}
	return basePattern[index]
}
