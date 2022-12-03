package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
)

func main_day2() {
	var lines [300]string
	var linesLen, err = utilities.ReadFileToStringArray("day2_input.txt", lines[0:])
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return
	}

	var doubleApp = 0;
	var tripleApp = 0;
	for index := 0; index < linesLen; index++ {
		var letterMap = make(map[byte]int)
		var line = lines[index]
		for letterIndex := 0; letterIndex < len(line); letterIndex++ {
			letterCount, letterFound := letterMap[line[letterIndex]]
			if letterFound {
				letterMap[line[letterIndex]] = letterCount + 1
			} else {
				letterMap[line[letterIndex]] = 1
			}
		}

		var doubleFound bool
		var tripleFound bool
		for _, value := range letterMap {
			doubleFound = doubleFound || (value == 2)
			tripleFound = tripleFound || (value == 3)
		}

		if doubleFound {
			doubleApp++
		}
		if tripleFound {
			tripleApp++
		}
	}

	fmt.Printf("Part 1: coveredInches %d\n", doubleApp*tripleApp)

	var mapOfVariants = make(map[string]bool)
	for index := 0; index < linesLen; index++ {
		var currentLine = lines[index]
		for letterIndex := 0; letterIndex < len(currentLine); letterIndex++ {
			var currentVariant = currentLine[:letterIndex] + "_" + currentLine[letterIndex + 1:]
			_, variantFound := mapOfVariants[currentVariant]
			if variantFound {
				fmt.Println("Part 2: " + currentVariant)
				return
			}

			mapOfVariants[currentVariant] = true
		}
	}
}
