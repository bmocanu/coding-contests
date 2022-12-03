package aoc_2018

import (
	"fmt"
	"io/ioutil"
)

var indexArr []int

func day5React(content []byte, byteToExclude byte) int {
	var contentLen = len(content)
	var finalLen = 0
	if indexArr == nil {
		indexArr = make([]int, contentLen)
	}

	for contentPos := 0; contentPos < contentLen; contentPos++ {
		if content[contentPos] == byteToExclude || content[contentPos] == byteToExclude+32 {
			indexArr[contentPos] = -1
		} else {
			indexArr[contentPos] = contentPos
			finalLen++
		}
	}

	var recentReduction = true
	var loops = 0
	for recentReduction {
		recentReduction = false
		loops++
		for arrPos := 0; arrPos < contentLen-1; arrPos++ {
			if indexArr[arrPos] >= 0 {
				var nextPos = arrPos + 1
				for nextPos < contentLen && indexArr[nextPos] < 0 {
					nextPos++
				}

				if nextPos < contentLen {
					var diff = (int8)(content[indexArr[arrPos]] - content[indexArr[nextPos]])
					if diff == 32 || diff == -32 {
						recentReduction = true;
						indexArr[arrPos] = -1
						indexArr[nextPos] = -1
						finalLen = finalLen - 2
						arrPos = arrPos - 2
						if arrPos < 0 {
							arrPos = -1
						}
					}
				}
			}
		}
	}

	fmt.Println("Loops: ", loops)

	return finalLen
}

func main_day5() {
	var content, err = ioutil.ReadFile("day5_input.txt")
	if err != nil {
		fmt.Println("Error reading input file", err)
		return
	}

	fmt.Println("Part 1: ", day5React(content[0:], 0))
	// fmt.Println("Part 1: ", react(content, 'A'))

	//var index byte
	//for index = 'A'; index < 'Z'; index++ {
	//	fmt.Println("Part 2: ", day5React(content[0:], index))
	//}
}
