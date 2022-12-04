package aoc_2018

import (
	"fmt"
	"github.com/bmocanu/code-tryouts/go/utilities"
	"math"
	"os"
)

type Point struct {
	x    int
	y    int
	velX int
	velY int
}

var points = make([]Point, 0)

func streamInputForDay10(line string) {
	var newPoint Point
	var _, err = fmt.Sscanf(line, "position=<%d,  %d> velocity=<%d, %d>", &newPoint.x, &newPoint.y, &newPoint.velX, &newPoint.velY)
	if err != nil {
		fmt.Println("Error parsing input line ", err)
		return
	}
	points = append(points, newPoint)
}

func day10Part1() {
	for moveIndex := 0; moveIndex < 10880; moveIndex++ {
		for pointIndex := 0; pointIndex < len(points); pointIndex++ {
			points[pointIndex].x += points[pointIndex].velX
			points[pointIndex].y += points[pointIndex].velY
		}

		var closePoints int
		for index1 := 0; index1 < len(points)-1; index1++ {
			for index2 := index1 + 1; index2 < len(points); index2++ {
				if utilities.Abs(points[index1].x-points[index2].x) <= 1 {
					if utilities.Abs(points[index1].y-points[index2].y) <= 1 {
						closePoints++
						break
					}
				}
			}
		}

		if closePoints >= len(points)-10 {
			fmt.Println("Aligned in ", moveIndex, " steps")
			printMatrix()
			break
		}
	}
}

func printMatrix() {
	var minX = math.MaxInt32
	var minY = math.MaxInt32
	var maxX = math.MinInt32
	var maxY = math.MinInt32
	var diffX, diffY int

	for index := 0; index < len(points); index++ {
		minX = utilities.Min(minX, points[index].x)
		minY = utilities.Min(minY, points[index].y)
		maxX = utilities.Max(maxX, points[index].x)
		maxY = utilities.Max(maxY, points[index].y)
	}

	var width, height int
	if minX < 0 {
		width = maxX - minX + 1
		diffX = -minX
	} else {
		width = maxX + 1
	}

	if minY < 0 {
		height = maxY - minY + 1
		diffY = -minY
	} else {
		height = maxY + 1
	}

	var matrix = make([][]int, width)
	for index := 0; index < width; index++ {
		matrix[index] = make([]int, height)
	}

	for index := 0; index < len(points); index++ {
		matrix[points[index].x+diffX][points[index].y+diffY] = 1
	}

	for y := 0; y < height; y++ {
		for x := 0; x < width; x++ {
			fmt.Print(string((byte)(32 + matrix[x][y]*3)))
		}
		fmt.Println()
	}

}

func main() {
	var inputStr = os.Args[1]
	err := utilities.StreamFileAsStringLines(inputStr, streamInputForDay10)
    if err != nil {
		fmt.Println("Error reading input file: "+inputStr, err)
	}

	day10Part1()
}
