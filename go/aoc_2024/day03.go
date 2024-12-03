package main

import (
	"bufio"
	"fmt"
	"strconv"

	//"math"
	"os"
	"regexp"
	//"strconv"
)

func main() {
	fileDesc, _ := os.Open("aoc_2024/day03_input.txt")
	defer fileDesc.Close()
	scanner := bufio.NewScanner(fileDesc)
	var line = ""
	for scanner.Scan() {
		line = line + scanner.Text()
	}
	//var mulRegex = regexp.MustCompile("(mul)\\(([0-9]{1,3}),([0-9]{1,3})\\)")
	//var doRegex = regexp.MustCompile("(do)\\(\\)")
	//var dontRegex = regexp.MustCompile("(don't)\\(\\)")

	var regex = regexp.MustCompile("(do\\(\\)|don't\\(\\)|mul\\(([0-9]{1,3}),([0-9]{1,3})\\))")
	var matches = regex.FindAllStringSubmatch(line, -1)

	var sum = 0
	var mulEnabled = true
	for _, match := range matches {
		var instruction = match[1]
		if instruction == "do()" {
			mulEnabled = true
		} else if instruction == "don't()" {
			mulEnabled = false
		} else if mulEnabled {
			var num1, _ = strconv.Atoi(match[2])
			var num2, _ = strconv.Atoi(match[3])
			sum += num1 * num2
		}
	}
	fmt.Println(sum)
}

//func getNextMatch(text string, regexArr []regexp.Regexp) []string {
//	var minIndex = math.MaxInt
//	var minMatch = []string
//	for _, match := range regexArr {
//		var result = match.FindStringSubmatchIndex(text)
//		if len(result) > 0 {
//
//		}
//	}
//}
