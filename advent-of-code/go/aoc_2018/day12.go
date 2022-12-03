package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"os"
	"strings"
)

var initialState string
var padding = ".........." // ten dots
var ruleMap = make(map[string]string)

func streamInputForDay12(line string) {
	if strings.HasPrefix(line, "initial state:") {
		initialState = strings.TrimSpace(line[15:])
	} else if strings.Contains(line, "=>") {
		var arrowIndex = strings.Index(line, "=>")
		var potConfig = strings.TrimSpace(line[:arrowIndex])
		var potResult = strings.TrimSpace(line[arrowIndex+2:])
		ruleMap[potConfig] = potResult
	}
}

func day12Part1() int {
	var genCount = 20
	var currentState = padding + initialState + padding
	var offset = -len(padding)

	for genIndex := 1; genIndex <= genCount; genIndex++ {
		currentState = newGen(currentState)
		if !strings.HasSuffix(currentState, padding) {
			currentState = currentState + "."
		}
	}

	var sum = 0
	for potIndex := 0; potIndex < len(currentState); potIndex++ {
		if currentState[potIndex] == '#' {
			sum += potIndex + offset
		}
	}

	return sum
}

func day12Part2() int64 {
	var currentState = padding + initialState + padding
	var offset = -len(padding)
	var oldState string
	// fmt.Printf("%d %s - %d\n", 0, currentState, len(currentState))

	var repetitiveGen int
	for repetitiveGen = 1; repetitiveGen < 2000; repetitiveGen++ {
		oldState = currentState
		currentState = newGen(currentState)
		if strings.HasPrefix(currentState, padding) {
			currentState = currentState[1:] + "."
			offset++
		}
		if !strings.HasSuffix(currentState, padding) {
			currentState = currentState + "."
		}
		// fmt.Printf("%d %s - %d %d\n", repetitiveGen, currentState, len(currentState), offset)
		if currentState == oldState {
			break
		}
	}

	var sum int64 = 0
	var pots int
	for potIndex := 0; potIndex < len(currentState); potIndex++ {
		if currentState[potIndex] == '#' {
			sum += int64(potIndex)
			pots++
		}
	}

	fmt.Println("Sum=", sum)
	fmt.Println("Pots=", pots)
	fmt.Println("RepetitiveGen=", repetitiveGen)
	fmt.Println("Offset=", offset)
	return sum + int64(pots)*(50000000000-int64(repetitiveGen)+int64(offset))
}

func newGen(currentState string) string {
	var stateLen = len(currentState)
	var newState = ".."
	for potIndex := 2; potIndex < stateLen-2; potIndex++ {
		var potResult, found = ruleMap[currentState[potIndex-2:potIndex+3]]
		if found {
			newState = newState + potResult
		} else {
			newState = newState + currentState[potIndex:potIndex+1]
		}
	}
	return newState + ".."
}

func main_day12() {
	var inputStr = os.Args[1]
	err := utilities.StreamFileAsStringLines(inputStr, streamInputForDay12);
	if err != nil {
		fmt.Println("Error reading input file: "+inputStr, err)
	}

	fmt.Println("Part 1: ", day12Part1())
	fmt.Println("Part 2: ", day12Part2())
}
