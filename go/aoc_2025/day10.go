package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"math"
	"reflect"
	"strconv"
	"strings"
)

var machineCount int
var lightsMtx [][]bool
var targetLightsMtx [][]bool
var buttonSetMtx [][][]int
var buttonBinMtx [][][]int
var joltageMtx [][]int

func main10() {
	var lines [300]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day10_input.txt", lines[0:])

	machineCount = lineCount
	lightsMtx = make([][]bool, machineCount)
	targetLightsMtx = make([][]bool, machineCount)
	buttonSetMtx = make([][][]int, machineCount)
	buttonBinMtx = make([][][]int, machineCount)
	joltageMtx = make([][]int, machineCount)
	for machineIndex := 0; machineIndex < machineCount; machineIndex++ {
		var currentLine = lines[machineIndex]
		var currentFields = strings.Fields(currentLine)

		// Parse the lights setup
		var lightsString = currentFields[0][1 : len(currentFields[0])-1]
		lightsMtx[machineIndex] = make([]bool, len(lightsString))
		targetLightsMtx[machineIndex] = make([]bool, len(lightsString))
		for lightIndex := 0; lightIndex < len(lightsString); lightIndex++ {
			lightsMtx[machineIndex][lightIndex] = false
			targetLightsMtx[machineIndex][lightIndex] = lightsString[lightIndex] == '#'
		}

		// Parse the joltage
		var currentJoltageField = currentFields[len(currentFields)-1]
		var currentJoltageNrs = strings.Split(currentJoltageField[1:len(currentJoltageField)-1], ",")
		joltageMtx[machineIndex] = make([]int, len(currentJoltageNrs))
		for joltageIndex := 0; joltageIndex < len(currentJoltageNrs); joltageIndex++ {
			joltageMtx[machineIndex][joltageIndex], _ = strconv.Atoi(currentJoltageNrs[joltageIndex])
		}

		// Parse the button config
		buttonSetMtx[machineIndex] = make([][]int, len(currentFields)-2)
		buttonBinMtx[machineIndex] = make([][]int, len(currentFields)-2)
		for buttonSetIndex := 0; buttonSetIndex < len(currentFields)-2; buttonSetIndex++ {
			var currentConfigFields = strings.Split(currentFields[buttonSetIndex+1][1:len(currentFields[buttonSetIndex+1])-1], ",")
			buttonSetMtx[machineIndex][buttonSetIndex] = make([]int, len(currentConfigFields))
			buttonBinMtx[machineIndex][buttonSetIndex] = make([]int, len(joltageMtx[machineIndex]))
			for buttonIndex := 0; buttonIndex < len(currentConfigFields); buttonIndex++ {
				var convValue, _ = strconv.Atoi(currentConfigFields[buttonIndex])
				buttonSetMtx[machineIndex][buttonSetIndex][buttonIndex] = convValue
				buttonBinMtx[machineIndex][buttonSetIndex][convValue] = 1
			}
		}
	}

	//fmt.Println(buttonBinMtx)

	var part1Sum = 0
	var part2Sum = 0
	for machineIndex := 0; machineIndex < machineCount; machineIndex++ {
		//part1Sum += calculateShortestLightConfig(machineIndex, make([]int, len(buttonSetMtx[machineIndex])), 0, 0, math.MaxInt)
		var maxFromJoltage = getMaxFromJoltage(joltageMtx[machineIndex])
		var p2Result = calculateShortestJoltageConfig(machineIndex, maxFromJoltage, make([]int, len(buttonSetMtx[machineIndex])), 0, 0, math.MaxInt)
		fmt.Printf("Machine %d has result %d\n", machineIndex, p2Result)
		part2Sum += p2Result
	}

	fmt.Println("Part1: ", part1Sum) // 488
	fmt.Println("Part2: ", part2Sum) // 488

}

func calculateShortestJoltageConfig(machineIndex int, maxFromJoltage int, config []int, configIndex int, buttonPresses int, minButtonPresses int) int {
	if configIndex >= len(config) {
		if testJoltageConfig(config, machineIndex) {
			if buttonPresses < minButtonPresses {
				return buttonPresses
			}
		}
		return minButtonPresses
	}
	for value := 0; value < maxFromJoltage; value++ {
		config[configIndex] = value
		minButtonPresses = calculateShortestJoltageConfig(machineIndex, maxFromJoltage, config, configIndex+1, buttonPresses+value, minButtonPresses)
	}
	return minButtonPresses
}

func testJoltageConfig(buttonConfig []int, machineIndex int) bool {
	var currentJoltage = make([]int, len(joltageMtx[machineIndex]))
	for index := 0; index < len(buttonConfig); index++ {
		for pressIndex := 0; pressIndex < buttonConfig[index]; pressIndex++ {
			for innerButtonIndex := 0; innerButtonIndex < len(buttonSetMtx[machineIndex][index]); innerButtonIndex++ {
				currentJoltage[buttonSetMtx[machineIndex][index][innerButtonIndex]]++
			}
		}
	}

	return reflect.DeepEqual(currentJoltage, joltageMtx[machineIndex])
}

func getMaxFromJoltage(joltage []int) int {
	var joltageMax = 0
	for _, v := range joltage {
		if v > joltageMax {
			joltageMax = v
		}
	}
	return joltageMax
}

// ----------------------------------------------------------------------------------------------------

func calculateShortestLightConfig(machineIndex int, buttonConfig []int, index int, buttonPresses int, minButtonPresses int) int {
	if index >= len(buttonConfig) {
		if testButtonPressConfig(buttonConfig, machineIndex) {
			if buttonPresses < minButtonPresses {
				return buttonPresses
			}
		}
		return minButtonPresses
	}
	for value := 0; value < 3; value++ {
		buttonConfig[index] = value
		minButtonPresses = calculateShortestLightConfig(machineIndex, buttonConfig, index+1, buttonPresses+value, minButtonPresses)
	}
	return minButtonPresses
}

func testButtonPressConfig(buttonConfig []int, machineIndex int) bool {
	var lightsSetup = make([]bool, len(targetLightsMtx[machineIndex]))
	for index := 0; index < len(buttonConfig); index++ {
		for pressIndex := 0; pressIndex < buttonConfig[index]; pressIndex++ {
			for innerButtonIndex := 0; innerButtonIndex < len(buttonSetMtx[machineIndex][index]); innerButtonIndex++ {
				lightsSetup[buttonSetMtx[machineIndex][index][innerButtonIndex]] = !lightsSetup[buttonSetMtx[machineIndex][index][innerButtonIndex]]
			}
		}
	}

	return reflect.DeepEqual(lightsSetup, targetLightsMtx[machineIndex])
}

// ----------------------------------------------------------------------------------------------------

//
//func calculateShortestJoltageConfig(machineIndex int, buttonConfig []int, index int, buttonPresses int, minButtonPresses int) int {
//	if index >= len(buttonConfig) {
//		if testJoltageConfig(buttonConfig, machineIndex) {
//			if buttonPresses < minButtonPresses {
//				return buttonPresses
//			}
//		}
//		return minButtonPresses
//	}
//	for value := 0; value < 5; value++ {
//		buttonConfig[index] = value
//		minButtonPresses = calculateShortestJoltageConfig(machineIndex, buttonConfig, index+1, buttonPresses+value, minButtonPresses)
//	}
//	return minButtonPresses
//}

//func testJoltageConfig(buttonConfig []int, machineIndex int) bool {
//	var joltageSetup = make([]int, len(joltageMtx[machineIndex]))
//	for index := 0; index < len(buttonConfig); index++ {
//		for pressIndex := 0; pressIndex < buttonConfig[index]; pressIndex++ {
//			for innerButtonIndex := 0; innerButtonIndex < len(buttonSetMtx[machineIndex][index]); innerButtonIndex++ {
//				joltageSetup[buttonSetMtx[machineIndex][index][innerButtonIndex]]++
//			}
//		}
//	}
//
//	return reflect.DeepEqual(joltageSetup, joltageMtx[machineIndex])
//}

//func calculateShortestJoltageConfig(machineIndex int) {
//	var initialJoltage = generateInitialJoltage(machineIndex)
//	var combMap = make(map[string]int)
//	var ntpList = new(utilities.LinkedList)
//	ntpList.Init()
//	var firstNode = ntpList.Add()
//	firstNode.Value = 0
//	firstNode.ValueIntArr = initialJoltage
//	combMap[convertToKey(initialJoltage)] = 0
//	var targetJoltage = joltageMtx[machineIndex]
//	var targetJoltageKey = convertToKey(joltageMtx[machineIndex])
//	var targetJoltageReached = false
//
//	for ntpList.Length() > 0 && !targetJoltageReached {
//		var currentJoltageNode, currentNrOfCombs = pickNextNode(ntpList, combMap)
//		var currentJoltage = currentJoltageNode.ValueIntArr
//		var currentJoltageKey = convertToKey(currentJoltage)
//		ntpList.Remove(currentJoltageNode)
//
//		for buttonSetIndex := 0; buttonSetIndex < len(buttonSetMtx[machineIndex]); buttonSetIndex++ {
//			var newJoltage = updateJoltage(currentJoltage, buttonSetMtx[machineIndex][buttonSetIndex])
//			if isNewJoltageValid(newJoltage, targetJoltage) {
//				var newJoltageKey = convertToKey(newJoltage)
//				fmt.Println("L=", ntpList.Length(), " - Cur=", currentJoltageKey, " - New=", newJoltageKey, " - Comb=", currentNrOfCombs)
//				if newJoltageKey == targetJoltageKey {
//					targetJoltageReached = true
//					combMap[targetJoltageKey] = currentNrOfCombs + 1
//					break
//				}
//				var existingNrOfCombs, exists = combMap[newJoltageKey]
//				if !exists {
//					combMap[newJoltageKey] = currentNrOfCombs + 1
//					var newNode = ntpList.Add()
//					newNode.ValueIntArr = newJoltage
//				} else {
//					if existingNrOfCombs > currentNrOfCombs+1 {
//						combMap[newJoltageKey] = currentNrOfCombs + 1
//					}
//				}
//			}
//		}
//	}
//	var targetValue, _ = combMap[targetJoltageKey]
//	fmt.Printf("Machine %d has result %d\n", machineIndex, targetValue)
//	day10Part2SumMutex.Lock()
//	day10Part2Sum += targetValue
//	day10Part2SumMutex.Unlock()
//}
