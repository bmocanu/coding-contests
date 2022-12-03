package main

import (
	"../utils"
	"fmt"
	"strconv"
)

var moonPos = make([]*utils.Point3D, 4)
var moonVelocities = make([]*utils.Point3D, 4)
var moonCount = 4

func main12() {
	var fileLines = make([]string, moonCount)
	utils.ReadFileToStringArray("aoc_2019/day12_input_mine.txt", fileLines)

	for index := 0; index < moonCount; index++ {
		moonPos[index] = new(utils.Point3D)
		fmt.Sscanf(fileLines[index], "<x=%d, y=%d, z=%d>", &moonPos[index].X, &moonPos[index].Y, &moonPos[index].Z)
		moonVelocities[index] = new(utils.Point3D)
	}

	for step := 0; step < 1000; step++ {
		day12AdjustVelocity()
		day12ApplyVelocity()
	}

	var totalSum = 0
	for moon := 0; moon < moonCount; moon++ {
		totalSum += utils.SumOfAbsCoordinatesOfPoint3D(moonPos[moon]) *
			utils.SumOfAbsCoordinatesOfPoint3D(moonVelocities[moon])
	}

	fmt.Printf("Probl 1 result: %d\n", totalSum)

	for index := 0; index < moonCount; index++ {
		moonPos[index] = new(utils.Point3D)
		fmt.Sscanf(fileLines[index], "<x=%d, y=%d, z=%d>", &moonPos[index].X, &moonPos[index].Y, &moonPos[index].Z)
		moonVelocities[index] = new(utils.Point3D)
	}

	var forX = day12CalculateCoordRepetition(utils.ArrayOfSingleCoordOfPoints3D(moonPos, 0))
	var forY = day12CalculateCoordRepetition(utils.ArrayOfSingleCoordOfPoints3D(moonPos, 1))
	var forZ = day12CalculateCoordRepetition(utils.ArrayOfSingleCoordOfPoints3D(moonPos, 2))

	fmt.Printf("%d - %d - %d => \n", forX, forY, forZ)

	fmt.Printf("Probl 2 result: %d\n", utils.SmallestCommonMultiplier([]int{forX, forY, forZ}))
}

func day12AdjustVelocity() {
	for moon1 := 0; moon1 < moonCount-1; moon1++ {
		for moon2 := moon1 + 1; moon2 < moonCount; moon2++ {
			moonVelocities[moon1].X -= utils.CompareInt(moonPos[moon1].X, moonPos[moon2].X)
			moonVelocities[moon2].X += utils.CompareInt(moonPos[moon1].X, moonPos[moon2].X)
			moonVelocities[moon1].Y -= utils.CompareInt(moonPos[moon1].Y, moonPos[moon2].Y)
			moonVelocities[moon2].Y += utils.CompareInt(moonPos[moon1].Y, moonPos[moon2].Y)
			moonVelocities[moon1].Z -= utils.CompareInt(moonPos[moon1].Z, moonPos[moon2].Z)
			moonVelocities[moon2].Z += utils.CompareInt(moonPos[moon1].Z, moonPos[moon2].Z)
		}
	}
}

func day12ApplyVelocity() {
	for moon := 0; moon < moonCount; moon++ {
		utils.AddPoint3DByPoint3D(moonPos[moon], moonVelocities[moon])
	}
}

func day12CalculateCoordRepetition(points []int) int {
	var vels = [4]int{0, 0, 0, 0}
	var hashMap = make(map[string]int)
	var step = 0
	fmt.Print("--------------------------------------------\n")

	for true {
		var currentHash = hashOfValues(points, vels[:])
		fmt.Printf("step=%d, p1=%d, p2=%d, p3=%d, p4=%d, hash=%s\n", step, points[0], points[1], points[2], points[3], currentHash)
		var _, found = hashMap[currentHash]
		if found {
			return step
		}
		step++
		hashMap[currentHash] = 1
		for index1 := 0; index1 < moonCount-1; index1++ {
			for index2 := index1 + 1; index2 < 4; index2++ {
				vels[index1] -= utils.CompareInt(points[index1], points[index2])
				vels[index2] += utils.CompareInt(points[index1], points[index2])
			}
		}
		for index := 0; index < moonCount; index++ {
			points[index] += vels[index]
		}
	}

	return 0
}

func hashOfValues(points []int, vels []int) string {
	var result = ""
	for index := 0; index < len(points); index++ {
		result = result + strconv.Itoa(points[index])
	}
	for index := 0; index < len(vels); index++ {
		result = result + strconv.Itoa(vels[index])
	}
	return result
}
