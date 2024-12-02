package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
	"unicode"
)

func main() {
	fileDesc, err := os.Open("aoc_2023/day01_input.txt")
	if err != nil {
		panic(err)
	}
	defer fileDesc.Close()

	var lines []string
	scanner := bufio.NewScanner(fileDesc)
	for scanner.Scan() {
		line := scanner.Text()
		lines = append(lines, line)
	}

	var sum = 0
	for _, line := range lines {
		var num = 0
		var lastDigit = 0
		for _, char := range line {
			if unicode.IsDigit(char) {
				lastDigit = int(char - '0')
				if num == 0 {
					num = lastDigit
				}
			}
		}
		num = num*10 + lastDigit
		sum += num
	}
	fmt.Println("Part 1: ", sum)

	// ----------------------------------------------------------------------------------------------------
	textToDigits := map[string]int{
		"one":   1,
		"two":   2,
		"three": 3,
		"four":  4,
		"five":  5,
		"six":   6,
		"seven": 7,
		"eight": 8,
		"nine":  9,
	}

	sum = 0
	for _, line := range lines {
		var num = 0
		var lastDigit = 0
		for index, char := range line {
			var digitAtThisPos = 0
			if unicode.IsDigit(char) {
				digitAtThisPos = int(char - '0')
			} else {
				digitAtThisPos = getDigitByText(line[index:], textToDigits)
			}
			if digitAtThisPos > 0 {
				lastDigit = digitAtThisPos
				if num == 0 {
					num = lastDigit
				}
			}
		}
		num = num*10 + lastDigit
		sum += num
	}
	fmt.Println("Part 2: ", sum)
}

func getDigitByText(line string, textToDigitsMap map[string]int) int {
	for prefix, digit := range textToDigitsMap {
		if strings.HasPrefix(line, prefix) {
			return digit
		}
	}
	return 0
}
