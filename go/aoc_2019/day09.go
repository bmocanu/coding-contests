package main

import (
	"../intcode"
	"../utils"
)

// Part 1: Intcode output: 3839402290
// Part 2: Intcode output: 35734

func main09() {
	var initialData = make([]int, 10000)
	var fileAsString = utils.ReadFileToString("aoc_2019/day09_input.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	var outputBus = new(intcode.StdOutputBus)
	var inputBus = new(intcode.SimpleDataBus)
	inputBus.Push(1)

	var iProc = new(intcode.Processor)
	iProc.Init(100000000, initialData, inputBus, outputBus)
	iProc.Config.LogAll = true
	iProc.Execute()

	inputBus.Push(2)
	iProc.Init(100000000, initialData, inputBus, outputBus)
	iProc.Execute()
}
