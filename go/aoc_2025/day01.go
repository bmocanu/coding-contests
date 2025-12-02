package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"strconv"
)

func main01() {
	var lines [5000]string
	var linesLen = utilities.ReadFileToStringArray("aoc_2025/day01_input.txt", lines[0:])
	var currentNum = 50
	var part1Index0Hits = 0
	var part2Index0Hits = 0
	//var part2IndexIncrementedForThisLine = false
	for index := 0; index < linesLen; index++ {
		//part2IndexIncrementedForThisLine = false
		var currentLine = lines[index]
		var currentInc, _ = strconv.Atoi(currentLine[1:])

		for ; currentInc > 0; currentInc-- {
			if currentLine[0] == 'L' {
				currentNum--
			} else {
				currentNum++
			}
			if currentNum < 0 {
				currentNum = 99
			}
			if currentNum > 99 {
				currentNum = 0
			}
			if currentNum == 0 {
				part2Index0Hits++
			}
		}

		if currentNum == 0 {
			part1Index0Hits++
		}
	}

	fmt.Println("Part 1: ", part1Index0Hits) // 1172
	fmt.Println("Part 2: ", part2Index0Hits) // 6932
}
