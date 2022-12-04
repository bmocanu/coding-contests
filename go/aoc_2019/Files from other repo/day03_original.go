package main

//import (
//	"fmt"
//	"github.com/bmocanu/code-tryouts/go/utilities"
//	"strconv"
//	"strings"
//)
//
//const MATRIX_WIDTH = 25000
//const MATRIX_HEIGHT = 25000
//
//var wireMatrix [MATRIX_WIDTH][MATRIX_HEIGHT] byte
//
//func main() {
//	var inputLines = make([]string, 2)
//	var _, err = utilities.ReadFileToStringArray("aoc-2019/day03_input.txt", inputLines)
//	if err != nil {
//		panic(fmt.Sprintf("Cannot read file: %v", err))
//		return
//	}
//
//	var centerX = MATRIX_WIDTH / 2
//	var centerY = MATRIX_HEIGHT / 2
//	var minDistance = MATRIX_WIDTH
//	var minTotalLengthToIntersection = MATRIX_WIDTH * MATRIX_HEIGHT
//	var firstWireCommands = strings.Split(inputLines[0], ",")
//
//	for wireIndex := 0; wireIndex < 2; wireIndex++ {
//		var commands = strings.Split(inputLines[wireIndex], ",")
//		var wireX = centerX
//		var wireY = centerY
//		var wireLength = 0
//		for commandIndex := 0; commandIndex < len(commands); commandIndex++ {
//			var currentCommand = commands[commandIndex][:1]
//			var currentNrSteps, _ = strconv.Atoi(commands[commandIndex][1:])
//			var dirX, dirY = utilities.GetDirDeltaByStringUDLR(currentCommand)
//			for stepIndex := 0; stepIndex < currentNrSteps; stepIndex++ {
//				wireX += dirX
//				wireY += dirY
//				wireLength++
//				switch wireIndex {
//				case 0:
//					wireMatrix[wireX][wireY] = byte(wireLength)
//				case 1:
//					if wireMatrix[wireX][wireY] > 0 {
//						var currentDistance = manhDistance(wireX, wireY, centerX, centerY)
//						if currentDistance < minDistance {
//							minDistance = currentDistance
//						}
//						var otherWireIntersectionLength = day03_getFirstWireDistanceTo(wireX, wireY, firstWireCommands, centerX, centerY)
//						if wireLength+otherWireIntersectionLength < minTotalLengthToIntersection {
//							minTotalLengthToIntersection = wireLength + otherWireIntersectionLength
//						}
//					}
//				}
//			}
//		}
//	}
//
//	fmt.Printf("Probl 1 result: %d\n", minDistance)
//	fmt.Printf("Probl 2 result: %d\n", minTotalLengthToIntersection)
//}
//
//func manhDistance(x1 int, y1 int, x2 int, y2 int) int {
//	return utilities.Abs(x1-x2) + utilities.Abs(y1-y2)
//}
//
//func day03_getFirstWireDistanceTo(x int, y int, commands []string, centerX int, centerY int) int {
//	var wireX = centerX
//	var wireY = centerY
//	var wireLength = 0
//	for commandIndex := 0; commandIndex < len(commands); commandIndex++ {
//		var currentCommand = commands[commandIndex][:1]
//		var currentNrSteps, _ = strconv.Atoi(commands[commandIndex][1:])
//		var dirX, dirY = utilities.GetDirDeltaByStringUDLR(currentCommand)
//		for stepIndex := 0; stepIndex < currentNrSteps; stepIndex++ {
//			wireX += dirX
//			wireY += dirY
//			wireLength++
//			if wireX == x && wireY == y {
//				return wireLength
//			}
//		}
//	}
//	return 0
//}
