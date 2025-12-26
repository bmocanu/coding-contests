package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"sort"
)

type Xy struct {
	X int
	Y int
}

type Edge struct {
	X1 int
	Y1 int
	X2 int
	Y2 int
}

type Area struct {
	X1   int
	Y1   int
	X2   int
	Y2   int
	Size int64
}

var allPoints []Xy
var allEdges []Edge
var allAreas []Area

func main09() {
	defer timer("main")()
	var lines [1000]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day09_input.txt", lines[0:])

	allPoints = make([]Xy, 0)
	allEdges = make([]Edge, 0)
	var prevPoint *Xy = nil
	var firstPoint *Xy = nil
	for index := 0; index < lineCount; index++ {
		var newPoint Xy
		fmt.Sscanf(lines[index], "%d,%d", &newPoint.X, &newPoint.Y)
		allPoints = append(allPoints, newPoint)
		if firstPoint == nil {
			firstPoint = &newPoint
		} else {
			allEdges = append(allEdges, *createEdge(prevPoint, &newPoint))
		}
		prevPoint = &newPoint
	}
	allEdges = append(allEdges, *createEdge(prevPoint, firstPoint))
	pointsCount = len(allPoints)

	allAreas = make([]Area, 0)
	for index1 := 0; index1 < pointsCount-1; index1++ {
		for index2 := index1 + 1; index2 < pointsCount; index2++ {
			var point1 = allPoints[index1]
			var point2 = allPoints[index2]
			var newArea Area
			newArea.X1 = point1.X
			newArea.Y1 = point1.Y
			newArea.X2 = point2.X
			newArea.Y2 = point2.Y
			newArea.Size = int64(utilities.Abs(point1.X-point2.X)+1) * int64(utilities.Abs(point1.Y-point2.Y)+1)
			allAreas = append(allAreas, newArea)
		}
	}

	sort.Slice(allAreas, func(i1 int, i2 int) bool {
		return allAreas[i1].Size > allAreas[i2].Size
	})

	fmt.Println("Part1: ", allAreas[0].Size) // 4771508457

	var part2Area *Area = nil
	for _, area := range allAreas {
		if !areaIntersectsAnEdge(&area) {
			part2Area = &area
			break
		}
	}

	fmt.Println("Part2: ", part2Area.Size) // 1539809693
}

func areaIntersectsAnEdge(area *Area) bool {
	var areaMinX = utilities.Min(area.X1, area.X2)
	var areaMinY = utilities.Min(area.Y1, area.Y2)
	var areaMaxX = utilities.Max(area.X1, area.X2)
	var areaMaxY = utilities.Max(area.Y1, area.Y2)

	for _, edge := range allEdges {
		var edgeMinX = utilities.Min(edge.X1, edge.X2)
		var edgeMinY = utilities.Min(edge.Y1, edge.Y2)
		var edgeMaxX = utilities.Max(edge.X1, edge.X2)
		var edgeMaxY = utilities.Max(edge.Y1, edge.Y2)

		// Case 1 - edge crosses the area from left (outside) to right (outside)
		if edgeMinX <= areaMinX && edgeMaxX >= areaMaxX && utilities.NrStrictlyBetween(edgeMinY, areaMinY, areaMaxY) {
			return true
		}

		// Case 2 - edge crosses the area from top (outside) to bottom (outside)
		if edgeMinY <= areaMinY && edgeMaxY >= areaMaxY && utilities.NrStrictlyBetween(edgeMinX, areaMinX, areaMaxX) {
			return true
		}

		// Case 3 - edge enters the area and one end is inside
		if (edge.X1 > areaMinX && edge.X1 < areaMaxX && edge.Y1 > areaMinY && edge.Y1 < areaMaxY) ||
			(edge.X2 > areaMinX && edge.X2 < areaMaxX && edge.Y2 > areaMinY && edge.Y2 < areaMaxY) {
			return true
		}
	}
	return false
}

func createEdge(point1 *Xy, point2 *Xy) *Edge {
	var newEdge = new(Edge)
	newEdge.X1 = point1.X
	newEdge.Y1 = point1.Y
	newEdge.X2 = point2.X
	newEdge.Y2 = point2.Y
	return newEdge
}
