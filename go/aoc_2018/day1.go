package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
)

func main_day1() {
	var numbers [1000]int
	var numbersLen, err = utilities.ReadFileToIntArray("day1_input.txt", numbers[0:])
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return
	}

	var sum = 0
	var seenFreq = make(map[int]bool)
	seenFreq[0] = true
	var duplicateFreqFound = false

	for loop := 0; !duplicateFreqFound; loop++ {
		for index := 0; index < numbersLen; index++ {
			sum += numbers[index]
			_, freqFound := seenFreq[sum]
			if freqFound {
				fmt.Printf("Part 2: freq seen twice: %d\n", sum)
				duplicateFreqFound = true;
				break
			} else {
				seenFreq[sum] = true
			}
		}

		if loop == 0 {
			fmt.Printf("Part 1 coveredInches: %d\n", sum)
		}
	}
}
