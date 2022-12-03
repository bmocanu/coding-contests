package main

import (
	"../intcode"
	"../utils"
	"fmt"
)

// Probl 1 result: [17406]
// Probl 2 result: [1047153]

var sequence = []int{0, 0, 0, 0, 0}
var sequenceMax = 0
var lastOut = 0
var initialData = make([]int, 2000)

var buses [5]intcode.SimpleDataBus
var amps [5]intcode.Processor

func main07() {
	var fileAsString = utils.ReadFileToString("aoc_2019/day07_input.txt")
	utils.SplitCsvStringToIntArray(fileAsString, initialData)

	for index := 0; index < len(amps); index++ {
		amps[index].Init(2000, initialData, &buses[index], &buses[(index+1)%5])
		amps[index].Config.Name = fmt.Sprintf("Amp%d", index)
		amps[index].Config.PauseOnEachOutput = true
		amps[index].Config.LogEachInstruction = true
	}

	day07calculate(0, false)
	fmt.Printf("Probl 1 result: [%d]\n", sequenceMax)

	sequenceMax = 0
	day07calculate(0, true)
	fmt.Printf("Probl 2 result: [%d]\n", sequenceMax)
}

func day07calculate(seqIndex int, loop bool) {
	if seqIndex < 5 {
		var start = 0
		if loop {
			start = 5
		}
		for index := start; index < start+5; index++ {
			var seqUsed = false
			for index2 := 0; index2 < seqIndex; index2++ {
				seqUsed = seqUsed || sequence[index2] == index
			}
			if !seqUsed {
				sequence[seqIndex] = index
				day07calculate(seqIndex+1, loop)
			}
		}
	} else {
		for index := 0; index < 5; index++ {
			amps[index].ResetMemory()
			buses[index].Reset()
			buses[index].Push(int64(sequence[index]))
		}
		buses[0].Push(0)

		var done = false
		lastOut = 0
		for !done {
			for ampIndex := 0; ampIndex < 5; ampIndex++ {
				done = done || amps[ampIndex].Execute()
			}

			lastOut = int(buses[0].Peek())
			if lastOut > sequenceMax {
				sequenceMax = lastOut
			}
			if !loop {
				break
			}
		}
	}
}
