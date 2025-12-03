package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

/*
Explanation of the Optimized Solution:

# Dynamic Programming Table:

dp[i][j] represents the maximum joltage we can form using the first i digits of the bank and selecting j batteries.
The table is filled iteratively, considering two options for each digit:
Skip the current digit.
Use the current digit and append it to the current joltage.

# Base Case:

If we need 0 batteries (j == 0), the maximum joltage is 0 regardless of the number of digits.

# Transition:

For each digit, we decide whether to skip it or use it. If we use it, we append it to the current joltage by multiplying the previous joltage by 10 and adding the current digit.

# Efficiency:

The time complexity is O(n * k), where n is the length of the bank and k is the number of batteries to turn on. This is significantly faster than the combinatorial approach.

# Result:

The result is stored in dp[n][numBatteries], where n is the total number of digits in the bank.
*/

func main() {
	// Open the input file
	file, err := os.Open("aoc_2025/day03_input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	// Initialize total output joltages for both parts
	totalOutputPart1 := 0
	totalOutputPart2 := 0

	// Read the file line by line
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		if len(line) < 2 {
			fmt.Println("Invalid bank of batteries:", line)
			continue
		}

		// Part 1: Find the largest joltage using exactly two batteries
		maxJoltagePart1 := findMaxJoltageDP(line, 2)
		totalOutputPart1 += maxJoltagePart1

		// Part 2: Find the largest joltage using exactly twelve batteries
		maxJoltagePart2 := findMaxJoltageDP(line, 12)
		totalOutputPart2 += maxJoltagePart2
	}

	// Check for errors during scanning
	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	// Print the results for both parts
	fmt.Println("Total Output Joltage (Part 1):", totalOutputPart1)
	fmt.Println("Total Output Joltage (Part 2):", totalOutputPart2)
}

// findMaxJoltageDP finds the largest possible joltage for a single bank of batteries
// by turning on exactly `numBatteries` batteries in their original order using dynamic programming
func findMaxJoltageDP(bank string, numBatteries int) int {
	n := len(bank)
	if n < numBatteries {
		return 0 // Not enough batteries to turn on
	}

	// Memoization table: dp[i][j] represents the maximum joltage we can form
	// using the first `i` digits of the bank and selecting `j` batteries
	dp := make([][]int, n+1)
	for i := range dp {
		dp[i] = make([]int, numBatteries+1)
	}

	// Base case: If we need 0 batteries, the maximum joltage is 0
	for i := 0; i <= n; i++ {
		dp[i][0] = 0
	}

	// Fill the DP table
	for i := 1; i <= n; i++ {
		for j := 1; j <= numBatteries; j++ {
			// Option 1: Skip the current digit
			dp[i][j] = dp[i-1][j]

			// Option 2: Use the current digit
			if j > 0 {
				currentDigit, _ := strconv.Atoi(string(bank[i-1]))
				dp[i][j] = max(dp[i][j], dp[i-1][j-1]*10+currentDigit)
			}
		}
	}

	// The result is the maximum joltage we can form using all `n` digits and `numBatteries` batteries
	return dp[n][numBatteries]
}

// max returns the maximum of two integers
func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
