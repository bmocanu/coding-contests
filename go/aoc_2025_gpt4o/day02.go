package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

// Function to check if a number is invalid for part 1 (exactly two repeated sequences)
func isInvalidIDPart1(id int) bool {
	s := strconv.Itoa(id)
	n := len(s)
	if n%2 != 0 {
		return false
	}
	mid := n / 2
	return s[:mid] == s[mid:]
}

// Function to check if a number is invalid for part 2 (any sequence repeated at least twice)
func isInvalidIDPart2(id int) bool {
	s := strconv.Itoa(id)
	n := len(s)

	// Check for any substring repeated at least twice
	for length := 1; length <= n/2; length++ {
		if n%length != 0 {
			continue
		}
		repeated := true
		for i := 0; i < n; i += length {
			if s[:length] != s[i:i+length] {
				repeated = false
				break
			}
		}
		if repeated {
			return true
		}
	}
	return false
}

// Function to parse the input ranges and calculate the sum of invalid IDs
func sumInvalidIDs(input string, part int) int {
	ranges := strings.Split(input, ",")
	totalSum := 0

	for _, r := range ranges {
		bounds := strings.Split(r, "-")
		if len(bounds) != 2 {
			continue
		}

		start, err1 := strconv.Atoi(bounds[0])
		end, err2 := strconv.Atoi(bounds[1])
		if err1 != nil || err2 != nil {
			continue
		}

		for id := start; id <= end; id++ {
			if (part == 1 && isInvalidIDPart1(id)) || (part == 2 && isInvalidIDPart2(id)) {
				totalSum += id
			}
		}
	}

	return totalSum
}

func main02() {
	// Open the input file
	file, err := os.Open("aoc_2025_ai/day02_input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	// Read the input from the file
	scanner := bufio.NewScanner(file)
	var input string
	for scanner.Scan() {
		input = scanner.Text()
	}

	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	// Solve for part 1
	resultPart1 := sumInvalidIDs(input, 1)
	fmt.Println("Sum of invalid IDs (Part 1):", resultPart1)

	// Solve for part 2
	resultPart2 := sumInvalidIDs(input, 2)
	fmt.Println("Sum of invalid IDs (Part 2):", resultPart2)
}
