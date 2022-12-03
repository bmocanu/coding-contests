package main

import (
	"../utils"
	"fmt"
)
import "../intcode"

func main11() {
	var initialData = make([]int, 10000)
	var fileAsString = utils.ReadFileToString("aoc_2019/day11_input.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	var inputBus = new(intcode.SimpleDataBus)
	var outputBus = new(intcode.SimpleDataBus)
	var iProc = new(intcode.Processor)
	iProc.Init(1000000, initialData, inputBus, outputBus)
	iProc.Config.PauseOnEmptyInput = true
	var mtx = new(utils.FlexStruct)

	mtx.Init()
	var posX, posY, dir = 20, 20, 0
	var paintedPanels = 0
	var programDone bool
	mtx.SetInt(posX, posY, 0)
	for !programDone {
		var point = mtx.Point(posX, posY)
		inputBus.Push(int64(point.Value))
		programDone = iProc.Execute()

		var colorToPaint, dirAdjustment = int(outputBus.Pull()), int(outputBus.Pull())
		point.Value = colorToPaint
		if !point.Marked {
			paintedPanels++
		}
		point.Marked = true

		dir = utils.CycleInt(dir+(dirAdjustment*2-1), 0, 3)
		posX, posY = utils.AdjustByDirectionDelta0123(posX, posY, dir)
	}

	fmt.Printf("Probl 1 result: %d\n", paintedPanels)

	posX, posY, dir = 20, 20, 0
	mtx.Init()
	mtx.SetInt(posX, posY, 1)
	iProc.ResetMemory()
	inputBus.Reset()
	outputBus.Reset()
	programDone = false
	for !programDone {
		var point = mtx.Point(posX, posY)
		inputBus.Push(int64(point.Value))
		programDone = iProc.Execute()

		var colorToPaint, dirAdjustment = int(outputBus.Pull()), int(outputBus.Pull())
		point.Value = colorToPaint
		point.Marked = colorToPaint == 1

		dir = utils.CycleInt(dir+(dirAdjustment*2-1), 0, 3)
		posX, posY = utils.AdjustByDirectionDelta0123(posX, posY, dir)
	}

	mtx.Print()
}
