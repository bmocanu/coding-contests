package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"sort"
)

var points []*utilities.Point3D
var pointsCount int
var usedDistancesMap map[int]float64
var usedDistancesArray []int
var maxCircuitIndex int

func main08() {
	defer timer("main")()
	fmt.Println("Reading input data...")
	var lines [1005]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day08_input.txt", lines[:])
	points = make([]*utilities.Point3D, lineCount)
	pointsCount = lineCount
	for index := 0; index < lineCount; index++ {
		var newPoint = new(utilities.Point3D)
		newPoint.Value = -1
		newPoint.Index = index
		fmt.Sscanf(lines[index], "%d,%d,%d", &newPoint.X, &newPoint.Y, &newPoint.Z)
		points[index] = newPoint
	}

	usedDistancesMap = make(map[int]float64)
	usedDistancesArray = make([]int, 0)
	fmt.Println("Generating all the distances...")
	for index1 := 0; index1 < pointsCount-1; index1++ {
		for index2 := index1 + 1; index2 < pointsCount; index2++ {
			var distance = utilities.Distance3D(points[index1], points[index2])
			var uniqueIndex = utilities.PackedIndex3D(points[index1], points[index2])
			usedDistancesMap[uniqueIndex] = distance
			usedDistancesArray = append(usedDistancesArray, uniqueIndex)
		}
	}

	fmt.Println("Sorting the distances...")
	sort.Slice(usedDistancesArray, func(i1, i2 int) bool {
		return usedDistancesMap[usedDistancesArray[i1]] < usedDistancesMap[usedDistancesArray[i2]]
	})

	maxCircuitIndex = 0
	var circuitCount = 0
	for conIndex := 0; conIndex < len(usedDistancesArray); conIndex++ {
		var currentUniqueIndex = usedDistancesArray[conIndex]
		var point1 = points[utilities.Part1FromPackedIndex(currentUniqueIndex)]
		var point2 = points[utilities.Part2FromPackedIndex(currentUniqueIndex)]
		if point1.Value == -1 && point2.Value == -1 {
			point1.Value = maxCircuitIndex
			point2.Value = maxCircuitIndex
			maxCircuitIndex++
			circuitCount++
		} else if point1.Value == -1 && point2.Value != -1 {
			point1.Value = point2.Value
		} else if point1.Value != -1 && point2.Value == -1 {
			point2.Value = point1.Value
		} else if point1.Value != point2.Value {
			// point1 circuit adopts point2 circuit
			var point2Index = point2.Value
			for _, point := range points {
				if point.Value == point2Index {
					point.Value = point1.Value
				}
			}
			circuitCount--
		}
		if conIndex == 999 {
			printPart1()
		}
		if conIndex > 1 && circuitCount == 1 {
			var allPointsConnected = true
			for _, point := range points {
				if point.Value == -1 {
					allPointsConnected = false
				}
			}
			if allPointsConnected {
				printPart2(point1, point2)
				return
			}
		}
	}

	panic("Failed to solve the problem")
}

func printPart1() {
	var circuits = make([][]*utilities.Point3D, maxCircuitIndex)
	for _, point := range points {
		if point.Value != -1 {
			circuits[point.Value] = append(circuits[point.Value], point)
		}
	}
	sort.Slice(circuits, func(i1, i2 int) bool {
		a := circuits[i1]
		b := circuits[i2]
		return len(a) > len(b)
	})
	fmt.Println("Part1: ", len(circuits[0])*len(circuits[1])*len(circuits[2])) // 24360
}

func printPart2(p1 *utilities.Point3D, p2 *utilities.Point3D) {
	fmt.Println("Part 2: ", p1.X*p2.X)
}
