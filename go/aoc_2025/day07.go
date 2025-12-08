package main

import (
	utilities "coding-contests/utils"
	"fmt"
)

const (
	typeSpace    = 1
	typeStart    = 2
	typeSplitter = 3
	typeBeam     = 4
)

func main07() {
	defer timer("main")()
	var lines [1000]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day07_input.txt", lines[0:])
	var teleMap utilities.FlexStruct
	teleMap.Init()
	teleMap.Parse(lines[0:lineCount], ".,1,S,2,^,3")

	var part1Count = 0
	var beamsMovedOnce = true
	teleMap.GetFirstPointByType(typeStart).Mark().SetValue(1) // the Start is in timeline 1
	for beamsMovedOnce {
		beamsMovedOnce = false
		for _, point := range teleMap.AllPointsMarked() {
			point.Unmark()
			if point.Y < teleMap.Height()-1 {
				beamsMovedOnce = true
				var pointBelow = teleMap.Point(point.X, point.Y+1)
				switch pointBelow.Type {
				case typeSpace:
					pointBelow.Type = typeBeam
					pointBelow.Value = point.Value
					pointBelow.Mark()
				case typeSplitter:
					var pointLeft = teleMap.PointOrNil(pointBelow.X-1, pointBelow.Y)
					var pointRight = teleMap.PointOrNil(pointBelow.X+1, pointBelow.Y)
					if pointLeft.Type == typeSpace {
						pointLeft.Type = typeBeam
						pointLeft.Value = point.Value
						pointLeft.Mark()
					} else if pointLeft.Type == typeBeam {
						pointLeft.Value += point.Value
					}
					if pointRight.Type == typeSpace {
						pointRight.Type = typeBeam
						pointRight.Value = point.Value
						pointRight.Mark()
					} else if pointRight.Type == typeBeam {
						pointRight.Value += point.Value
					}
					part1Count++
				case typeBeam:
					pointBelow.Value += point.Value
				}
			}
		}
	}

	fmt.Println("Part1: ", part1Count)                      // 1619
	fmt.Println("Part2: ", teleMap.SumValuesOnBottomLine()) // 23607984027985
	// teleMap.PrintByType("1,.,2,S,3,█,4,VAL", 3)
}
