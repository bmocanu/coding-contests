package main

import (
	utilities "coding-contests/utils"
	"fmt"
)

const (
	emptySpace  = 1 // not used, placed here for reference
	paperRoll   = 2
	typeMapping = ".,1,@,2"
)

func main04() {
	defer timer("main")() // <-- The trailing () is the deferred call
	var lines [1000]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day04_input.txt", lines[0:])

	var wallStruct utilities.FlexStruct
	wallStruct.Init()
	wallStruct.Parse(lines[0:lineCount], typeMapping)

	fmt.Println("Part1: ", removeRolls(wallStruct.DeepClone(), true))  // 1491
	fmt.Println("Part2: ", removeRolls(wallStruct.DeepClone(), false)) // 8722
}

func removeRolls(wallStruct *utilities.FlexStruct, stopAfterFirstRound bool) int {
	var rollsRemoved = 0
	for {
		var atLeastOneRollRemoved = false
		wallStruct.UnmarkAllPoints()
		for _, point := range wallStruct.AllPointsByType(paperRoll) {
			var rollsAround = 0
			for dir := 0; dir < 8; dir++ {
				var currentPoint = wallStruct.PointOrNil(utilities.PointCoordsByDelta0to7(point, dir))
				if currentPoint != nil && currentPoint.Type == paperRoll {
					rollsAround++
				}
			}
			if rollsAround < 4 {
				rollsRemoved++
				atLeastOneRollRemoved = true
				point.Mark()
			}
		}
		wallStruct.SetTypeForAllPointsMarked(emptySpace)
		if stopAfterFirstRound || !atLeastOneRollRemoved {
			return rollsRemoved
		}
	}
}
