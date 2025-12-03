package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"math"
	"strconv"
	"sync"
	"time"
)

var part2SumMutex sync.Mutex
var part2WaitGroup sync.WaitGroup
var part2Sum int64

func main() {
	defer timer("main")() // <-- The trailing () is the deferred call
	var lines [1000]string
	var linesCount = utilities.ReadFileToStringArray("aoc_2025/day03_input.txt", lines[0:])

	var part1Sum = 0
	for lineIndex := range linesCount {
		var currentLine = lines[lineIndex]
		var currentLineMax = -1
		for index1 := 0; index1 < len(currentLine)-1; index1++ {
			var nr1, _ = strconv.Atoi(string(currentLine[index1]))
			for index2 := index1 + 1; index2 < len(currentLine); index2++ {
				var nr2, _ = strconv.Atoi(string(currentLine[index2]))
				if nr1*10+nr2 > currentLineMax {
					currentLineMax = nr1*10 + nr2
				}
			}
		}
		part1Sum = part1Sum + currentLineMax

		part2WaitGroup.Add(1)
		go part2LineMax(currentLine)
	}

	part2WaitGroup.Wait()

	fmt.Println("Part1: ", part1Sum) // 17142
	fmt.Println("Part2: ", part2Sum) // 169935154100102
}

func part2LineMax(line string) {
	defer part2WaitGroup.Done()
	var digitArray = utilities.StringToDigitsArray(line)
	var maxOverall = p2Backtrack(digitArray, len(digitArray), 0, 0, 0, -1)
	part2SumMutex.Lock()
	part2Sum += maxOverall
	part2SumMutex.Unlock()
	return
}

func p2Backtrack(digitArray []int, digitArrayLen int, currentIndex int, currentNr int64, nrOfActiveDigits int, maxOverall int64) int64 {
	if nrOfActiveDigits >= 12 {
		if currentNr > maxOverall {
			return currentNr
		} else {
			return maxOverall
		}
	}
	if currentIndex >= digitArrayLen {
		return maxOverall
	}
	if (currentNr+1)*int64(math.Pow(10, float64(12-nrOfActiveDigits))) < maxOverall {
		return maxOverall
	}
	maxOverall = p2Backtrack(digitArray, digitArrayLen, currentIndex+1, currentNr*10+int64(digitArray[currentIndex]), nrOfActiveDigits+1, maxOverall)
	return p2Backtrack(digitArray, digitArrayLen, currentIndex+1, currentNr, nrOfActiveDigits, maxOverall)
}

func timer(name string) func() {
	start := time.Now()
	return func() {
		fmt.Printf("%s took %v\n", name, time.Since(start))
	}
}
