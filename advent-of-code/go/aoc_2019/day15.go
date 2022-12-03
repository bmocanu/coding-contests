package main

import (
	"../intcode"
	"../utils"
	"fmt"
)

const (
	robotAnswerWall         = 0
	robotAnswerEmpty        = 1
	robotAnswerOxygenSystem = 2

	structureWall   = 1
	structureEmpty  = 0
	structureOxygen = 2
	structureStart  = 3
)

var inputBus = new(intcode.SimpleDataBus)
var outputBus = new(intcode.SimpleDataBus)
var iProc *intcode.Processor
var mtx *utils.FlexStruct

func main15() {
	var initialData = make([]int, 10000)
	var fileAsString = utils.ReadFileToString("aoc_2019/day15_mine.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	iProc = new(intcode.Processor)
	iProc.Config.PauseOnEmptyInput = true
	iProc.Init(1000000, initialData, inputBus, outputBus)

	mtx = new(utils.FlexStruct)
	mtx.Init()

	var robotX = 25
	var robotY = 25
	var direction = 1
	mtx.Point(robotX, robotY).SetType(structureStart).TrailMark().SetValue(0)

	for true {
		var robotAnswer = communicateWithRobot(direction)
		var newX, newY = getNewPosition(robotX, robotY, direction)
		switch robotAnswer {
		case robotAnswerEmpty:
			mtx.Point(newX, newY).SetType(structureEmpty).TrailMark().SetValueIfZero(mtx.Point(robotX, robotY).Value + 1)
			robotX, robotY = newX, newY
		case robotAnswerWall:
			mtx.Point(newX, newY).SetType(structureWall).TrailMark()
		case robotAnswerOxygenSystem:
			mtx.Point(newX, newY).SetType(structureOxygen).TrailMark().SetValueIfZero(mtx.Point(robotX, robotY).Value + 1)
			robotX, robotY = newX, newY
		}
		direction = getNewDirection(robotX, robotY)
		if direction < 0 {
			break
		}
	}

	mtx.PrintByType("0, ,1,#,2,X,3,S", 1)
	fmt.Printf("Probl 1 result: %d\n", mtx.GetFirstPointByType(structureOxygen).Value)

	mtx.SetAllPointValues(0)
	mtx.GetFirstPointByType(structureStart).SetType(structureEmpty)
	mtx.GetFirstPointByType(structureOxygen).SetValue(1)
	var nrOfMinutes = 1
	var newSpreadHappened = true
	for newSpreadHappened {
		newSpreadHappened = false
		for _, point := range mtx.AllPoints() {
			if point.Value == nrOfMinutes {
				for dir := 1; dir <= 4; dir++ {
					var pointNear = mtx.PointOrNil(getNewPosition(point.X, point.Y, dir))
					if pointNear != nil && pointNear.Type == structureEmpty && pointNear.Value == 0 {
						pointNear.Value = nrOfMinutes + 1
						newSpreadHappened = true
					}
				}
			}
		}
		nrOfMinutes++
	}

	mtx.PrintByType("0,VAL,1,###,2,XXX,3,SSS", 3)
	fmt.Printf("Probl 2 result: %d\n", nrOfMinutes-2)
}

func communicateWithRobot(direction int) int {
	inputBus.Push(int64(direction))
	if iProc.Execute() {
		fmt.Printf("Program is done!!!")
		return 0
	}
	return int(outputBus.Pull())
}

func getNewDirection(robotX int, robotY int) int {
	var bestDir = -1
	var bestDirMinTrails = 100
	for dir := 1; dir <= 4; dir++ {
		var point = mtx.Point(getNewPosition(robotX, robotY, dir))
		if point.Type == structureEmpty {
			if point.TrailMarkCount < bestDirMinTrails {
				bestDirMinTrails = point.TrailMarkCount
				bestDir = dir
			}
		}
	}
	if bestDir < 0 {
		fmt.Printf("Cannot decide on a direction! I might be stucked!!!!!!")
	}
	return bestDir
}

func getNewPosition(posX int, posY int, dir int) (newX int, newY int) {
	newX = posX
	newY = posY
	switch dir {
	case 1:
		newY--
	case 2:
		newY++
	case 3:
		newX--
	case 4:
		newX++
	}
	return
}
