package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"strconv"
)

var currentGuardId int
var currentGuardFallingTime int
var maxGuardId int
var maxGuardSleepTime int
var maxGuardMinute int
var guardMap = make(map[int][]int)

func handleStringLineForDay4(line string) {
	// [1518-02-24 24:02] Guard #101 begins shift
	var year, month, day, hour, minute int
	var action, guardIdString string
	var _, err = fmt.Sscanf(line, "[%d-%d-%d %d:%d] %s %s", &year, &month, &day, &hour, &minute, &action, &guardIdString);
	if err != nil {
		fmt.Println("Error parsing line: " + line)
		return
	}
	var relativeTime = minute // basically only the minutes from the 00 hour are important, as guards can only fall asleep past midnight

	if "Guard" == action {
		currentGuardId, _ = strconv.Atoi(guardIdString[1:])
		_, timelineFound := guardMap[currentGuardId]
		if !timelineFound {
			guardMap[currentGuardId] = make([]int, 60)
		}
	} else if "falls" == action {
		currentGuardFallingTime = relativeTime
	} else if "wakes" == action {
		var guardTimeline, _ = guardMap[currentGuardId]
		for index := currentGuardFallingTime; index < relativeTime; index++ {
			guardTimeline[index]++
			if guardTimeline[index] > maxGuardSleepTime {
				maxGuardId = currentGuardId
				maxGuardSleepTime = guardTimeline[index]
				maxGuardMinute = index
			}
		}
	}
}

func main_day4() {
	err := utilities.StreamFileAsStringLines("day4_input_cleaned.txt", handleStringLineForDay4);
	if err != nil {
		fmt.Println("Error reading input file", err)
	}
	fmt.Println(guardMap)
	fmt.Println(maxGuardId)
	fmt.Println(maxGuardSleepTime)
	fmt.Println(maxGuardMinute)
	fmt.Printf("Part 1: %d\n", maxGuardId*maxGuardMinute)
	// now Part 2 was tricky for me to understand. What I did, I printed the map as above, then looked at the max
	// time, and chose the other guard, not the one designated for Part 1 (there are basically 2 guards with the
	// same max sleep time)
}
