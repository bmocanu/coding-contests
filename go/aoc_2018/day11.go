package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"math"
	"os"
	"strconv"
)

var gridSerialNumber int
var grid [300][300]int
var gridCache = make(map[int]int)

func day11part1() (int, int) {
	var maxPower = math.MinInt32
	var maxX = 0
	var maxY = 0

	for yPos := 0; yPos < 300; yPos++ {
		for xPos := 0; xPos < 300; xPos++ {
			grid[xPos][yPos] = powerLevel(xPos, yPos)
			if xPos >= 2 && yPos >= 2 {
				var fuelCellPower = fuelCellPower(xPos-2, yPos-2, 3)
				if fuelCellPower > maxPower {
					maxPower = fuelCellPower
					maxX = xPos - 2
					maxY = yPos - 2
				}
			}
		}
	}

	return maxX + 1, maxY + 1
}

var p2MaxPower, p2X, p2Y, p2Size int

func day11part2Cache(topX int, topY int, size int) int {
	var currentPower int

	if size == 1 {
		currentPower = grid[topX][topY]
	} else if size == 2 {
		currentPower = grid[topX][topY] + grid[topX+1][topY] + grid[topX][topY+1] + grid[topX+1][topY+1]
	} else {
		var found bool
		currentPower, found = gridCache[topX*1000000+topY*1000+size]
		if !found {
			var halfSize = size / 2
			var rem = size % 2
			currentPower = day11part2Cache(topX, topY, halfSize) +
				day11part2Cache(topX+halfSize, topY, halfSize+rem) +
				day11part2Cache(topX, topY+halfSize, halfSize+rem) +
				day11part2Cache(topX+halfSize+rem, topY+halfSize+rem, halfSize)
			if rem > 0 {
				currentPower -= grid[topX+halfSize][topY+halfSize]
			}
		}
	}

	gridCache[topX*1000000+topY*1000+size] = currentPower
	return currentPower
}

func day11part2() {
	for yPos := 0; yPos < 300; yPos++ {
		for xPos := 0; xPos < 300; xPos++ {
			var minSize = utilities.Min(300-xPos, 300-yPos)
			for size := 1; size <= minSize; size++ {
				var currentPower = day11part2Cache(xPos, yPos, size)
				if currentPower > p2MaxPower {
					p2MaxPower = currentPower
					p2X = xPos
					p2Y = yPos
					p2Size = size
				}
			}
		}
	}
}

func fuelCellPower(topX int, topY int, size int) int {
	var sum int
	for yInc := 0; yInc < size; yInc++ {
		for xInc := 0; xInc < size; xInc++ {
			sum += grid[topX+xInc][topY+yInc]
		}
	}
	return sum
}

func powerLevel(x int, y int) int {
	x = x + 1
	y = y + 1
	var rackId = x + 10
	var powerLevel = rackId * y
	powerLevel += gridSerialNumber
	powerLevel *= rackId
	powerLevel = (powerLevel % 1000) / 100
	return powerLevel - 5
}

func main_day11() {
	gridSerialNumber, _ = strconv.Atoi(os.Args[1])
	var p1X, p1Y = day11part1()
	fmt.Println("Part 1: ", p1X, p1Y)

	//fmt.Println("Part 2: building the cache")
	//day11part2Cache(0, 0, 300)
	day11part2()
	fmt.Println("Part 2: ", p2X+1, p2Y+1, p2Size, p2MaxPower)
}
