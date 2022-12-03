package aoc_2018

import (
	"fmt"
	"io/ioutil"
	"math"
)

func areOpposites(char1 byte, char2 byte) bool {
	var diff = (int8)(char1 - char2)
	return diff == 32 || diff == -32
}

func react(content []byte, filter byte) int {
	var contentLen = len(content)
	var result = make([]byte, contentLen)
	var resultPos = -1

	for contentPos := 0; contentPos < contentLen; contentPos++ {
		var thisByte = content[contentPos]
		if filter == thisByte || areOpposites(filter, thisByte) {
			continue
		}

		if resultPos < 0 {
			resultPos++
			result[resultPos] = thisByte
			continue
		}

		if areOpposites(result[resultPos], thisByte) {
			resultPos--
		} else {
			resultPos++
			result[resultPos] = thisByte
		}
	}

	return resultPos + 1
}

func main_day5mrb() {
	var content, err = ioutil.ReadFile("day5_input.txt")
	if err != nil {
		fmt.Println("Error reading input file", err)
		return
	}

	fmt.Println("Part 1: ", react(content[0:], 0))

	var filter byte
	var minLen = math.MaxInt32
	for filter = 'A'; filter <= 'Z'; filter++ {
		var thisLen = react(content[0:], filter)
		if thisLen < minLen {
			minLen = thisLen
		}
	}
	fmt.Println("Part 2: ", minLen)
}
