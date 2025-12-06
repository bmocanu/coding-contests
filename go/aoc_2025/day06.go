package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"strconv"
	"strings"
)

func main() {
	defer timer("main")()
	var lines [1000]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day06_input.txt", lines[0:])
	var maxLineLength = 0
	//var spaceRegex = regexp.MustCompile(`\w+`)
	var numbers = make([][]uint64, lineCount-1)
	for rowIndex := 0; rowIndex < lineCount-1; rowIndex++ {
		maxLineLength = utilities.Max(maxLineLength, len(lines[rowIndex]))
		var currentLineElements = strings.Fields(lines[rowIndex])
		numbers[rowIndex] = make([]uint64, len(currentLineElements))
		for elemIndex := 0; elemIndex < len(currentLineElements); elemIndex++ {
			numbers[rowIndex][elemIndex], _ = strconv.ParseUint(currentLineElements[elemIndex], 10, 32)
		}
	}
	var operations = strings.Fields(lines[lineCount-1])

	var part1Sum uint64 = 0
	for elemIndex := 0; elemIndex < len(numbers[0]); elemIndex++ {
		var result = numbers[0][elemIndex]
		for rowIndex := 1; rowIndex < len(numbers); rowIndex++ {
			if operations[elemIndex] == "+" {
				result += numbers[rowIndex][elemIndex]
			} else {
				result *= numbers[rowIndex][elemIndex]
			}
		}
		part1Sum += result
	}

	fmt.Println("Part1: ", part1Sum) // 5335495999141

	var part2Sum uint64 = 0
	var nrLines = lineCount - 1
	var nrColumns = maxLineLength
	var currentSum uint64 = 0
	var currentProd uint64 = 1
	var operationIndex = len(operations) - 1

	// input trimming in IDEA - gotta make up for that
	for lineIndex := 0; lineIndex < nrLines; lineIndex++ {
		for len(lines[lineIndex]) < maxLineLength {
			lines[lineIndex] += " "
		}
	}

	for colIndex := nrColumns - 1; colIndex >= 0; colIndex-- {
		var currentNum uint64 = 0
		for lineIndex := 0; lineIndex < nrLines; lineIndex++ {
			if lines[lineIndex][colIndex] != ' ' {
				var digit, _ = strconv.Atoi(string(lines[lineIndex][colIndex]))
				currentNum = currentNum*10 + uint64(digit)
			}
		}
		if currentNum != 0 {
			if operations[operationIndex] == "+" {
				currentSum += currentNum
			} else {
				currentProd *= currentNum
			}
		}
		if currentNum == 0 || colIndex == 0 {
			if operations[operationIndex] == "+" {
				part2Sum += currentSum
			} else {
				part2Sum += currentProd
			}
			operationIndex--
			currentSum = 0
			currentProd = 1
		}
	}

	fmt.Println("Part2: ", part2Sum) // 10142723156431
}
