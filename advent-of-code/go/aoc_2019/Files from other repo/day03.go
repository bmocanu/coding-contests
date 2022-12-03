package main

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"strconv"
	"strings"
)

const StartSize = 50000

var wireMatrix utilities.FlexibleIntMatrix

func main() {
	var inputLines = make([]string, 2)
	var _, err = utilities.ReadFileToStringArray("aoc-2019/day03_input.txt", inputLines)
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return
	}

	wireMatrix.Init()

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
			var dirX, dirY = utilities.GetDirDeltaByStringUDLR(commands[commandIndex][:1])
			for stepIndex := 0; stepIndex < currentNrSteps; stepIndex++ {
				wireX += dirX
				wireY += dirY
				wireLength++
				switch wireIndex {
				case 0:
					wireMatrix.Set(wireX, wireY, wireLength)
				case 1:
					var otherWireLength = wireMatrix.Get(wireX, wireY)
					if otherWireLength > 0 {
						var currentDistance = utilities.TaxicabDistance(wireX, wireY, centerX, centerY)
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
}
