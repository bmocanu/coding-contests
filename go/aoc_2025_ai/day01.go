package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func main01() {
	// Open the input file
	file, err := os.Open("aoc_2025_ai/day01_input.txt") // Replace "input.txt" with your input file path
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	// Initialize variables
	currentPosition := 50
	passwordPart1 := 0
	passwordPart2 := 0
	const dialSize = 100

	// Read the input line by line
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		if len(line) < 2 {
			continue
		}

		// Parse the direction and distance
		direction := line[0]
		distance, err := strconv.Atoi(line[1:])
		if err != nil {
			fmt.Println("Error parsing distance:", err)
			return
		}

		// Calculate the new position and count intermediate zeros
		if direction == 'L' {
			// Moving left (toward lower numbers)
			for i := 1; i <= distance; i++ {
				currentPosition = (currentPosition - 1 + dialSize) % dialSize
				if currentPosition == 0 {
					passwordPart2++
				}
			}
		} else if direction == 'R' {
			// Moving right (toward higher numbers)
			for i := 1; i <= distance; i++ {
				currentPosition = (currentPosition + 1) % dialSize
				if currentPosition == 0 {
					passwordPart2++
				}
			}
		}

		// Check if the dial points to 0 at the end of the rotation
		if currentPosition == 0 {
			passwordPart1++
		}
	}

	// Check for scanner errors
	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	// Output the passwords
	fmt.Println("Password (Part 1):", passwordPart1)
	fmt.Println("Password (Part 2):", passwordPart2)
}
