package main

import (
	"../utils"
	"fmt"
	"strconv"
	"strings"
)

// Probl 1 result: 258
// Probl 2 result: 12304

const StartSize = 20

var wireMatrix utils.FlexStruct

func main03() {
	var inputLines = make([]string, 2)
	utils.ReadFileToStringArray("aoc_2019/day03_input2.txt", inputLines)

	wireMatrix.Init()
	wireMatrix.AllPointsByName("this")

	var centerX = StartSize
	var centerY = StartSize
	var minDistance = StartSize
	var minTotalLengthToIntersection = StartSize * 2

	for wireIndex := 0; wireIndex < 2; wireIndex++ {
		var commands = strings.Split(inputLines[wireIndex], ",")
		var wireX = centerX
		var wireY = centerY
		var wireLength = 0
		for commandIndex := 0; commandIndex < len(commands); commandIndex++ {
			var currentNrSteps, _ = strconv.Atoi(commands[commandIndex][1:])
			var dirX, dirY = utils.GetDirectionDeltaByStringUDLR(commands[commandIndex][:1])
			for stepIndex := 0; stepIndex < currentNrSteps; stepIndex++ {
				wireX += dirX
				wireY += dirY
				wireLength++
				switch wireIndex {
				case 0:
					wireMatrix.SetInt(wireX, wireY, wireLength)
				case 1:
					var otherWireLength = wireMatrix.GetInt(wireX, wireY)
					if otherWireLength > 0 {
						var currentDistance = utils.TaxicabDistance(wireX, wireY, centerX, centerY)
						if currentDistance < minDistance {
							minDistance = currentDistance
						}
						if wireLength+otherWireLength < minTotalLengthToIntersection {
							minTotalLengthToIntersection = wireLength + otherWireLength
						}
					}
				}
			}
		}
	}

	fmt.Printf("Total matrix size: %d\n", wireMatrix.Size())
	fmt.Printf("Probl 1 result: %d\n", minDistance)
	fmt.Printf("Probl 2 result: %d\n", minTotalLengthToIntersection)
	wireMatrix.Print()
}
