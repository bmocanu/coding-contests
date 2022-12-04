package main

import (
	"../utils"
	"fmt"
	"strconv"
)

const (
	typeSpace   = 1
	typeBug     = 2
	typeMapping = "1,.,2,#"
)

func main() {
	var lines = make([]string, 64)
	utils.ReadFileToStringArray("aoc_2019/day24_mine.txt", lines)

	var mtx = new(utils.FlexStruct)
	mtx.Init()
	mtx.Parse(lines, fmt.Sprintf("#,%d,.,%d", typeBug, typeSpace))
	mtx.PrintByType(typeMapping, 1)

	var layoutMap = make(map[string]int)
	for true {
		var currentLayout = mtx.SerializeToString(typeMapping, 1)
		var _, found = layoutMap[currentLayout]
		if found {
			break
		}
		layoutMap[currentLayout] = 1
		var newMtx = mtx.DeepClone()
		for _, point := range mtx.AllPoints() {
			var bugsAround = 0
			for dir := 0; dir < 4; dir++ {
				var possiblePoint = mtx.PointOrNil(utils.PointCoordsByDelta0123(point, dir))
				if possiblePoint != nil && possiblePoint.Type == typeBug {
					bugsAround++
				}
			}
			if point.Type == typeBug && bugsAround != 1 {
				newMtx.PointByPoint(point).SetType(typeSpace)
			}
			if point.Type == typeSpace && (bugsAround == 1 || bugsAround == 2) {
				newMtx.PointByPoint(point).SetType(typeBug)
			}
		}
		mtx = newMtx
	}

	var repeatedBinaryLayout = mtx.SerializeToString("1,0,2,1", 1)
	fmt.Printf("Repeating layout: %s\n", repeatedBinaryLayout)

	// 1000010000110000010000111
	var result = 0
	var powerOf2 = 1
	for index := 0; index < len(repeatedBinaryLayout); index++ {
		if repeatedBinaryLayout[index] == '1' {
			result += powerOf2
		}
		powerOf2 = powerOf2 * 2
	}

	var strconvResult, _ = strconv.ParseInt(utils.Reverse(repeatedBinaryLayout), 2, 64)
	fmt.Printf("Part 1 result (by strconv): %d\n", strconvResult)
	fmt.Printf("Part 1 result: %d\n", result)
}
