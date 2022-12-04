package main

import (
	"../intcode"
	"../utils"
	"fmt"
)

const (
	structBlock  = 2
	structPaddle = 3
	structBall   = 4
)

func main13() {
	var initialData = make([]int, 10000)
	var fileAsString = utils.ReadFileToString("aoc_2019/day13_input.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	var inputBus = new(intcode.SimpleDataBus)
	var outputBus = new(intcode.SimpleDataBus)
	var iProc = new(intcode.Processor)
	iProc.Config.PauseOnEmptyInput = true
	iProc.Config.LogEachInstruction = false
	iProc.Init(1000000, initialData, inputBus, outputBus)

	var mtx = new(utils.FlexStruct)
	mtx.Init()

	iProc.Execute()
	for !outputBus.IsEmpty() {
		var outX, outY, outType = int(outputBus.Pull()), int(outputBus.Pull()), int(outputBus.Pull())
		mtx.Point(outX, outY).SetType(outType)
	}

	mtx.PrintByType("0,.,1,#,2,B,3,P,4,O", 1)
	fmt.Printf("Probl 1 result: %d\n", mtx.CountPointsOfType(structBlock))

	iProc.ResetMemory().Set(0, 2)

	var ballX = mtx.GetFirstPointByType(structBall).X
	var paddleX = mtx.GetFirstPointByType(structPaddle).X
	var score = 0
	var iProcFinished = false

	for !iProcFinished {
		iProcFinished = iProc.Execute()
		for !outputBus.IsEmpty() {
			var outX, outY, outType = int(outputBus.Pull()), int(outputBus.Pull()), int(outputBus.Pull())
			if outX == -1 && outY == 0 {
				score = outType
			} else {
				switch outType {
				case structPaddle:
					paddleX = outX
				case structBall:
					ballX = outX
				}
			}
		}
		inputBus.Push(int64(utils.CompareInt(ballX, paddleX)))
	}

	fmt.Printf("Probl 2 result: %d\n", score)
}
