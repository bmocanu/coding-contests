package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	fileDesc, err := os.Open("aoc_2024/day02_input.txt")
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
	}
	defer fileDesc.Close()

	var matrix = make([][]int, 0)

	scanner := bufio.NewScanner(fileDesc)
	for scanner.Scan() {
		var line = scanner.Text()
		var lineComps = strings.Split(line, " ")
		var matrixLine = make([]int, 0)
		for index := range lineComps {
			lineComp, _ := strconv.Atoi(lineComps[index])
			matrixLine = append(matrixLine, lineComp)
		}
		matrix = append(matrix, matrixLine)
	}

	var safeReports = 0
	for y := range matrix {
		for xToSkip := range matrix[y] {
			var reportIsSafe = true
			var dir = 0
			var dirSet = false
			var prevElement = 0
			var prevElementSet = false
			for x := 0; x < len(matrix[y]); x++ {
				if x != xToSkip {
					if prevElementSet {
						var absDif = abs(matrix[y][x] - prevElement)
						if !between(absDif, 1, 3) {
							reportIsSafe = false
							break
						}
						if !dirSet {
							dir = compare(matrix[y][x], prevElement)
							dirSet = true
						} else {
							if dir != compare(matrix[y][x], prevElement) {
								reportIsSafe = false
								break
							}
						}
					}
					prevElement = matrix[y][x]
					prevElementSet = true
				}
			}
			if reportIsSafe {
				safeReports++
				break
			}
		}
	}

	fmt.Println("Part 1: ", safeReports)
}

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

func between(x int, left int, right int) bool {
	return x >= left && x <= right
}

func compare(x int, y int) int {
	if x < y {
		return -1
	}
	if x > y {
		return 1
	}
	return 0
}
