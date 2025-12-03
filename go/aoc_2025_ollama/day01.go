package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	// Read the input file
	content, err := os.ReadFile("aoc_2025/day01_input.txt")
	if err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	// Split the content into lines
	lines := strings.Split(string(content), "\n")

	// Initialize the dial position and count
	current := 50
	count := 0

	for _, line := range lines {
		if line == "" {
			continue
		}

		// Extract direction and distance
		dir := line[0]
		distance, _ := strconv.Atoi(line[1:])

		// Compute the new position
		var new_pos int
		switch dir {
		case 'L':
			new_pos = (current - distance) % 100
		case 'R':
			new_pos = (current + distance) % 100
		}

		// Check if the new position is 0
		if new_pos == 0 {
			count++
		}

		// Update the current position
		current = new_pos
	}

	// Output the result
	fmt.Println("Password:", count)
}
