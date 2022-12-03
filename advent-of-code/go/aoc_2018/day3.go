package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"strconv"
	"strings"
)

var coveredInches int
var isolatedMat = make(map[int]bool)
var fabricMat [1000][1000]int

func handleStringLineForDay3(line string) {
	var posComAlt = strings.Index(line, "@")
	var posComma = strings.Index(line, ",")
	var posColon = strings.Index(line, ":")
	var posX = strings.Index(line, "x")
	var id, _ = strconv.Atoi(strings.TrimSpace(line[1:posComAlt]))
	var left, _ = strconv.Atoi(strings.TrimSpace(line[posComAlt+1 : posComma]))
	var top, _ = strconv.Atoi(strings.TrimSpace(line[posComma+1 : posColon]))
	var width, _ = strconv.Atoi(strings.TrimSpace(line[posColon+1 : posX]))
	var height, _ = strconv.Atoi(strings.TrimSpace(line[posX+1:]))

	var overlapped = false
	for x := left; x < left+width; x++ {
		for y := top; y < top+height; y++ {
			var matValue = fabricMat[x][y]
			if matValue == 0 {
				fabricMat[x][y] = -id
			} else {
				if matValue < 0 {
					coveredInches++
					matValue = -matValue
				}
				overlapped = true
				isolatedMat[matValue] = false
				fabricMat[x][y] = id;
			}
		}
	}

	if !overlapped {
		isolatedMat[id] = true
	}
}

func main_day3() {
	err := utilities.StreamFileAsStringLines("day3_input.txt", handleStringLineForDay3);
	if err != nil {
		fmt.Println("Error reading day3_input file", err)
	}
	fmt.Printf("Part 1: %d\n", coveredInches)
	fmt.Println("Part 2: ", isolatedMat)
}
