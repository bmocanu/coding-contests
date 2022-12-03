package main

import (
	"../utils"
	"fmt"
)

func main08() {
	var digits = utils.ReadFileToDigitsArray("aoc_2019/day08_input.txt")
	var width = 25
	var height = 6
	var layerCount = len(digits) / width / height
	var minNrOfZeros = 15000
	var result = 0
	for layer := 0; layer < layerCount; layer++ {
		var nrOfZeros = 0
		var nrOfOnes = 0
		var nrOfTwos = 0
		for index := 0; index < width*height; index++ {
			var currentDigit = digits[layer*width*height+index]
			switch currentDigit {
			case 0:
				nrOfZeros++
			case 1:
				nrOfOnes++
			case 2:
				nrOfTwos++
			}
		}
		if nrOfZeros < minNrOfZeros {
			minNrOfZeros = nrOfZeros
			result = nrOfOnes * nrOfTwos
		}
	}

	fmt.Printf("Problem 1 result: %d\n", result)

	var matrix = utils.InitIntMatrix2D(width, height, 2)
	for layer := 0; layer < layerCount; layer++ {
		for y := 0; y < height; y++ {
			for x := 0; x < width; x++ {
				var currentDigit = digits[layer*width*height+width*y+x]
				if currentDigit < 2 {
					if matrix[x][y] == 2 {
						matrix[x][y] = currentDigit
					}
				}
			}
		}
	}

	utils.PrintMatrix(matrix, "0, ,1,#")
}
