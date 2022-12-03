package main

import (
	"../utils"
	"fmt"
)

func main10() {
	var fileContent = make([]string, 30)
	utils.ReadFileToStringArray("aoc_2019/day10_input.txt", fileContent)
	var size = len(fileContent[0])

	var mtx = utils.FlexStruct{}
	mtx.Init()
	for y := 0; y < size; y++ {
		for x := 0; x < size; x++ {
			if fileContent[y][x] == "#"[0] {
				mtx.SetInt(x, y, 0)
			}
		}
	}

	for _, point1 := range mtx.AllPoints() {
		for _, point2 := range mtx.AllPoints() {
			if point2 != point1 {
				var isVisible = true
				for _, point3 := range mtx.AllPoints() {
					if point3 != point1 && point3 != point2 {
						if utils.PointsOnTheSameLine(point1, point2, point3) {
							isVisible = false
							break
						}
					}
				}
				if isVisible {
					point1.Value++
				}
			}
		}
	}

	var stPoint = mtx.GetPointWithMaxValue()
	fmt.Printf("Probl 1 result: %d - %d,%d\n", stPoint.Value, stPoint.X, stPoint.Y)

	var refPoint = utils.Point{X: stPoint.X, Y: 0}
	stPoint.Destroyed = true

	var asteroidsDestroyed = 0
	for true {
		var prevAngle float64 = -1
		for true {
			var minAngle float64 = 400
			var minDistance = float64(size * 2)
			var minPoint *utils.Point
			for _, currentPoint := range mtx.AllPoints() {
				if !currentPoint.Destroyed {
					var currentAngle = utils.AngleBetween3PointsInDegrees(&refPoint, stPoint, currentPoint)
					if currentAngle <= minAngle && currentAngle > prevAngle {
						var currentDistance = utils.Distance(stPoint, currentPoint)
						if currentAngle < minAngle || currentDistance < minDistance {
							minAngle = currentAngle
							minDistance = currentDistance
							minPoint = currentPoint
							fmt.Printf("%.2f ", minAngle)
						}
					}
				}
			}
			if minPoint != nil {
				asteroidsDestroyed++
				minPoint.Destroyed = true
				fmt.Printf("\n%d => %d - %d\n", asteroidsDestroyed, minPoint.X, minPoint.Y)
				prevAngle = minAngle
				if asteroidsDestroyed > 200 {
					return
				}
			} else {
				break
			}
		}
	}

}
