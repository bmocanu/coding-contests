package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"strconv"
	"strings"
)

func main() {
	var inputLine = utilities.ReadFileToString("aoc_2025/day02_input.txt")
	var inputIntervals = strings.Split(inputLine, ",")

	var part1Sum int64 = 0
	var part2Sum int64 = 0

	for intervalIndex := range inputIntervals {
		var interval = inputIntervals[intervalIndex]
		var intervalLimits = strings.Split(interval, "-")
		var leftLimit, _ = strconv.ParseInt(intervalLimits[0], 10, 64)
		var rightLimit, _ = strconv.ParseInt(intervalLimits[1], 10, 64)
		for cursor := leftLimit; cursor <= rightLimit; cursor++ {
			if isAMatchForPart1(cursor) {
				part1Sum += cursor
			}
			if isAMatchForPart2(cursor) {
				part2Sum += cursor
			}
		}
	}

	fmt.Println("Part1: ", part1Sum) // 43952536386
	fmt.Println("Part2: ", part2Sum) // 54486209192
}

func isAMatchForPart1(nr int64) bool {
	var nrStr = strconv.FormatInt(nr, 10)
	if len(nrStr)%2 == 0 {
		var leftStr = nrStr[0 : len(nrStr)/2]
		var rightStr = nrStr[len(nrStr)/2:]
		if leftStr == rightStr {
			return true
		}
	}
	return false
}

func isAMatchForPart2(nr int64) bool {
	var nrStr = strconv.FormatInt(nr, 10)
	var nrStrLen = len(nrStr)
	for div := 1; div <= nrStrLen/2; div++ {
		if nrStrLen%div == 0 {
			var seg = nrStr[0:div]
			if nrStr == strings.Repeat(seg, nrStrLen/div) {
				return true
			}
		}
	}
	return false
}
