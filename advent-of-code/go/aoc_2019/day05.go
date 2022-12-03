package main

import (
	"../intcode"
	"../utils"
)

//Intcode output: 7566643
//Intcode output: 9265694

func main05() {
	var fileAsString = utils.ReadFileToString("aoc_2019/day05_input.txt")
	var initialData = make([]int, 2000)
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	var inputBus = intcode.SimpleDataBus{}
	var processor = intcode.Processor{}
	processor.Init(2000, initialData, &inputBus, &intcode.StdOutputBus{})

	inputBus.Push(1)
	processor.Execute()

	inputBus.Push(5)
	processor.ResetMemory()
	processor.Execute()
}
