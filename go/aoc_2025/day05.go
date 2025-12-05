package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"strconv"
	"strings"
)

type Day05Interval struct {
	left  int64
	right int64
}

func (interval *Day05Interval) contains(value int64) bool {
	return interval.left <= value && value <= interval.right
}

var slicedIntervals = make([]Day05Interval, 0)

func main() {
	defer timer("main")()
	var lines [1200]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day05_input.txt", lines[0:])
	var intervals = make([]Day05Interval, 0)
	var ingredients = make([]int64, 0)

	var intervalMode = true
	for lineIndex := 0; lineIndex < lineCount; lineIndex++ {
		var currentLine = lines[lineIndex]
		if len(currentLine) == 0 {
			intervalMode = false
		}
		if intervalMode {
			var intervalParts = strings.Split(currentLine, "-")
			var left, _ = strconv.Atoi(intervalParts[0])
			var right, _ = strconv.Atoi(intervalParts[1])
			var newInterval = Day05Interval{int64(left), int64(right)}
			intervals = append(intervals, newInterval)
		} else {
			var newIngredient, _ = strconv.Atoi(currentLine)
			ingredients = append(ingredients, int64(newIngredient))
		}
	}

	var part1Count = 0
	for _, ingredient := range ingredients {
		for _, interval := range intervals {
			if interval.contains(ingredient) {
				part1Count++
				break
			}
		}
	}

	var part2Count int64 = 0
	for _, interval := range intervals {
		compareAndSlice(interval)
	}
	for _, interval := range slicedIntervals {
		part2Count += interval.right - interval.left + 1
	}

	fmt.Println("Part1: ", part1Count) // 862
	fmt.Println("Part2: ", part2Count) // 357907198933892
}

func compareAndSlice(interval Day05Interval) {
	if interval.left > interval.right {
		return
	}
	for _, slicedInterval := range slicedIntervals {
		if interval.left == slicedInterval.left {
			interval.left++
			compareAndSlice(interval)
			return
		}
		if interval.right == slicedInterval.right {
			interval.right--
			compareAndSlice(interval)
			return
		}
		if interval.left > slicedInterval.left && interval.right < slicedInterval.right {
			return
		}
		if interval.left < slicedInterval.left && interval.right > slicedInterval.right {
			var newSlicedInterval1 = Day05Interval{interval.left, slicedInterval.left - 1}
			compareAndSlice(newSlicedInterval1)
			var newSlicedInterval2 = Day05Interval{slicedInterval.right + 1, interval.right}
			compareAndSlice(newSlicedInterval2)
			return
		}
		if utilities.Between(interval.left, slicedInterval.left, slicedInterval.right) {
			var newSlicedInterval = Day05Interval{slicedInterval.right + 1, interval.right}
			compareAndSlice(newSlicedInterval)
			return
		}
		if utilities.Between(interval.right, slicedInterval.left, slicedInterval.right) {
			var newSlicedInterval = Day05Interval{interval.left, slicedInterval.left - 1}
			compareAndSlice(newSlicedInterval)
			return
		}
	}
	slicedIntervals = append(slicedIntervals, interval)
}
