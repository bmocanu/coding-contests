package main

import (
	"../intcode"
	"../utils"
	"fmt"
)

const (
	structSpace = 1
	structWall  = 2
	structRobot = 3
)

func main17() {
	var initialData = make([]int, 10000)
	var fileAsString = utils.ReadFileToString("aoc_2019/day17_input_mine.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	var inputBus = new(intcode.SimpleDataBus)
	var outputBus = new(intcode.SimpleDataBus)

	var iProc = new(intcode.Processor)
	iProc.Config.PauseOnEmptyInput = true
	iProc.Init(1000000, initialData, inputBus, outputBus)

	var mtx = new(utils.FlexStruct)
	mtx.Init()

	var x, y = 0, 0
	iProc.Execute()
	for !outputBus.IsEmpty() {
		switch outputBus.Pull() {
		case int64('#'):
			mtx.Point(x, y).SetType(structWall)
			x++
		case int64('.'):
			mtx.Point(x, y).SetType(structSpace)
			x++
		case int64(10):
			x = 0
			y++
		default:
			mtx.Point(x, y).SetType(structRobot)
			x++
		}
	}

	mtx.PrintByType("1,.,2,#,3,R", 1)

	var wallPoints = mtx.AllPointsByType(structWall)
	var sumParams = 0
	for _, point := range wallPoints {
		var pointsAround = 0
		for dir := 0; dir < 4; dir++ {
			if mtx.Point(utils.PointCoordsByDelta0123(point, dir)).Type == structWall {
				pointsAround++
			}
		}
		if pointsAround == 4 {
			sumParams += point.X * point.Y
		}
	}

	fmt.Printf("Part 1 result: %d\n", sumParams)
}
