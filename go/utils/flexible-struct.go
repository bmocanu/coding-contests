package utils

import (
	"fmt"
	"math"
	"strconv"
	"strings"
)

type FlexStruct struct {
	pointMap              map[int]*Point
	pointCount            int
	width                 int
	height                int
	positiveDelta         int
	atLeastOnePointMarked bool
}

// ----------------------------------------------------------------------------------------------------

func (matrix *FlexStruct) Init() *FlexStruct {
	matrix.pointMap = make(map[int]*Point)
	matrix.positiveDelta = 10000
	matrix.width = 0
	matrix.height = 0
	matrix.pointCount = 0
	return matrix
}

func (matrix *FlexStruct) Parse(input []string, typeMapping string) *FlexStruct {
	var typeMap = make(map[string]int)
	if len(typeMapping) > 0 {
		var typeMappingFragments = strings.Split(typeMapping, ",")
		for index := 0; index < len(typeMappingFragments); index += 2 {
			var value, _ = strconv.Atoi(typeMappingFragments[index+1])
			typeMap[typeMappingFragments[index]] = value
		}
	}
	for y := 0; y < len(input); y++ {
		for x := 0; x < len(input[y]); x++ {
			for char, typeValue := range typeMap {
				if input[y][x] == char[0] {
					matrix.Point(x, y).SetType(typeValue)
				}
			}
		}
	}
	return matrix
}

func (matrix *FlexStruct) SetInt(x int, y int, value int) *Point {
	var targetPoint = matrix.PointOrNil(x, y)
	if targetPoint == nil {
		targetPoint = new(Point)
		matrix.pointCount++
		matrix.pointMap[matrix.uniqueCoordinatesHash(x, y)] = targetPoint
		if x >= matrix.width {
			matrix.width = x + 1
		}
		if y >= matrix.height {
			matrix.height = y + 1
		}
	}
	targetPoint.Value = value
	targetPoint.X = x
	targetPoint.Y = y
	return targetPoint
}

func (matrix *FlexStruct) SetChar(x int, y int, value string) {
	matrix.SetInt(x, y, int(value[0]))
}

func (matrix *FlexStruct) SetTypeForAllPointsMarked(newType int) {
	for _, point := range matrix.pointMap {
		if point.Marked {
			point.Type = newType
		}
	}
}

func (matrix *FlexStruct) GetInt(x int, y int) int {
	var existingPoint = matrix.Point(x, y)
	if existingPoint != nil {
		return existingPoint.Value
	}
	return 0
}

func (matrix *FlexStruct) GetChar(x int, y int) string {
	return string(rune(matrix.GetInt(x, y)))
}

func (matrix *FlexStruct) Point(x int, y int) *Point {
	var point = matrix.PointOrNil(x, y)
	if point == nil {
		matrix.SetInt(x, y, 0)
		point = matrix.PointOrNil(x, y)
	}
	return point
}

func (matrix *FlexStruct) PointByPoint(thePoint *Point) *Point {
	return matrix.Point(thePoint.X, thePoint.Y)
}

func (matrix *FlexStruct) PointOrNil(x int, y int) *Point {
	var point, pointFound = matrix.pointMap[matrix.uniqueCoordinatesHash(x, y)]
	if pointFound {
		return point
	}
	return nil
}

func (matrix *FlexStruct) Size() int {
	return matrix.pointCount
}

func (matrix *FlexStruct) Width() int {
	return matrix.width
}

func (matrix *FlexStruct) Height() int {
	return matrix.height
}

func (matrix *FlexStruct) WidthAndHeight() (int, int) {
	return matrix.width, matrix.height
}

func (matrix *FlexStruct) AtLeastOnePointMarked() bool {
	return matrix.atLeastOnePointMarked
}

func (matrix *FlexStruct) AllPoints() map[int]*Point {
	return matrix.pointMap
}

func (matrix *FlexStruct) AllPointsMarked() map[int]*Point {
	var resultMap = make(map[int]*Point)
	for index, point := range matrix.pointMap {
		if point.Marked {
			resultMap[index] = point
		}
	}
	return resultMap
}

func (matrix *FlexStruct) AllPointsByType(theType int) map[int]*Point {
	var resultMap = make(map[int]*Point)
	for index, point := range matrix.pointMap {
		if point.Type == theType {
			resultMap[index] = point
		}
	}
	return resultMap
}

func (matrix *FlexStruct) AllPointsByName(name string) map[int]*Point {
	var resultMap = make(map[int]*Point)
	for index, point := range matrix.pointMap {
		if point.Name == name {
			resultMap[index] = point
		}
	}
	return resultMap
}

func (matrix *FlexStruct) AllPointsOnLine(y int) map[int]*Point {
	var resultMap = make(map[int]*Point)
	for index, point := range matrix.pointMap {
		if point.Y == y {
			resultMap[index] = point
		}
	}
	return resultMap
}

func (matrix *FlexStruct) SumValuesOnLine(y int) int {
	var resultSum = 0
	for _, point := range matrix.pointMap {
		if point.Y == y {
			resultSum += point.Value
		}
	}
	return resultSum
}

func (matrix *FlexStruct) SumValuesOnBottomLine() int {
	return matrix.SumValuesOnLine(matrix.Height() - 1)
}

func (matrix *FlexStruct) GetPointWithMaxValue() *Point {
	var maxValue = math.MinInt32
	var maxPoint *Point = nil
	for _, point := range matrix.pointMap {
		if point.Value > maxValue {
			maxValue = point.Value
			maxPoint = point
		}
	}
	return maxPoint
}

func (matrix *FlexStruct) GetFirstPointByType(theType int) *Point {
	for _, point := range matrix.pointMap {
		if point.Type == theType {
			return point
		}
	}
	return nil
}

func (matrix *FlexStruct) GetFirstPointByTypeAndName(theType int, name string) *Point {
	for _, point := range matrix.pointMap {
		if point.Type == theType && point.Name == name {
			return point
		}
	}
	return nil
}

func (matrix *FlexStruct) SetAllPointValues(value int) {
	for _, point := range matrix.pointMap {
		point.Value = value
	}
}

func (matrix *FlexStruct) SetAllPointsMarked(newMarked bool) {
	for _, point := range matrix.pointMap {
		point.Marked = newMarked
	}
	if !newMarked {
		matrix.atLeastOnePointMarked = false
	}
}

func (matrix *FlexStruct) UnmarkAllPoints() {
	for _, point := range matrix.pointMap {
		point.Marked = false
	}
	matrix.atLeastOnePointMarked = false
}

func (matrix *FlexStruct) HasPoint(x int, y int) bool {
	var _, pointFound = matrix.pointMap[matrix.uniqueCoordinatesHash(x, y)]
	return pointFound
}

func (matrix *FlexStruct) CountPointsOfType(theType int) int {
	var count = 0
	for _, point := range matrix.pointMap {
		if point.Type == theType {
			count++
		}
	}
	return count
}

func (matrix *FlexStruct) AddMissingPoints() {
	for x := 0; x < matrix.Width(); x++ {
		for y := 0; y < matrix.Height(); y++ {
			matrix.Point(x, y)
		}
	}
}

func (matrix *FlexStruct) DeepClone() *FlexStruct {
	var newMatrix = new(FlexStruct)
	newMatrix.pointMap = make(map[int]*Point)
	newMatrix.pointCount = matrix.pointCount
	newMatrix.width = matrix.width
	newMatrix.height = matrix.height
	newMatrix.positiveDelta = matrix.positiveDelta
	for key, value := range matrix.pointMap {
		var newPoint = new(Point)
		newPoint.X = value.X
		newPoint.Y = value.Y
		newPoint.Value = value.Value
		newPoint.Destroyed = value.Destroyed
		newPoint.Enabled = value.Enabled
		newPoint.Marked = value.Marked
		newPoint.TrailMarkCount = value.TrailMarkCount
		newPoint.Type = value.Type
		newPoint.Location = value.Location
		newPoint.Name = value.Name
		newMatrix.pointMap[key] = newPoint
	}
	for _, point := range matrix.pointMap {
		if point.Link != nil {
			var newPoint = newMatrix.Point(point.X, point.Y)
			var newLink = newMatrix.Point(point.Link.X, point.Link.Y)
			newPoint.Link = newLink
		}
	}
	return newMatrix
}

func (matrix *FlexStruct) Print() {
	fmt.Print("--------------------\n")
	var point *Point
	for y := 0; y < matrix.Height(); y++ {
		fmt.Print("|")
		for x := 0; x < matrix.Width(); x++ {
			point = matrix.Point(x, y)
			if point != nil {
				if point.Marked {
					fmt.Printf("%2s ", "#")
				} else if point.Value != 0 && !point.Destroyed {
					fmt.Printf("%2d ", point.Value)
				} else {
					fmt.Printf("%2s ", ".")
				}
			} else {
				fmt.Printf("%2s ", ".")
			}
		}
		fmt.Print("|\n")
	}
	fmt.Print("--------------------\n")
}

/*
TypeMapping = a CSV of key-Value items, to define the mapping when printing the content
"0, ,1,#" will result in all zeroes being printed as spaces, and all ones being printed as #
Special tokens that can be used:
- TMC = TrailMarkCount value
- VAL = Value of the node
- NME = The name of the node
- LOC = The location of the node
*/
func (matrix *FlexStruct) PrintByType(typeMapping string, padding int) {
	fmt.Print(matrix.internalPrintByType(typeMapping, padding, "\n", true))
}

func (matrix *FlexStruct) SerializeToString(typeMapping string, padding int) string {
	return matrix.internalPrintByType(typeMapping, padding, "", false)
}

// ----------------------------------------------------------------------------------------------------

func (matrix *FlexStruct) uniqueCoordinatesHash(x int, y int) int {
	var deltaX = x + matrix.positiveDelta
	var deltaY = y + matrix.positiveDelta
	return (deltaX+deltaY)*(deltaX+deltaY+1)/2 + deltaY
}

func (matrix *FlexStruct) internalPrintByType(typeMapping string, padding int, lineSeparator string, decorate bool) string {
	var typeMap = make(map[int]string)
	if len(typeMapping) > 0 {
		var typeMappingFragments = strings.Split(typeMapping, ",")
		for index := 0; index < len(typeMappingFragments); index += 2 {
			var leftValue, _ = strconv.Atoi(typeMappingFragments[index])
			typeMap[leftValue] = typeMappingFragments[index+1]
		}
	}
	var result = fmt.Sprintf("%s", lineSeparator)
	if decorate {
		result += fmt.Sprintf("+%s+%s", RepeatString("-", matrix.Width()*padding), lineSeparator)
	}
	var point *Point
	for y := 0; y < matrix.Height(); y++ {
		if decorate {
			result += fmt.Sprint("|")
		}
		for x := 0; x < matrix.Width(); x++ {
			point = matrix.PointOrNil(x, y)
			if point != nil {
				var char, charFound = typeMap[point.Type]
				if charFound {
					switch char {
					case "TMC":
						result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.TrailMarkCount)
					case "VAL":
						result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.Value)
					case "NME":
						result += fmt.Sprintf("%"+strconv.Itoa(padding)+"s", point.Name)
					case "LOC":
						result += fmt.Sprintf("%"+strconv.Itoa(padding)+"d", point.Location)
					default:
						result += fmt.Sprintf("%"+strconv.Itoa(padding)+"s", char)
					}
				} else {
					result += fmt.Sprint(RepeatString("?", padding))
				}
			} else {
				result += fmt.Sprint(RepeatString(" ", padding))
			}
		}
		if decorate {
			result += fmt.Sprint("|")
		}
		result += fmt.Sprintf("%s", lineSeparator)
	}

	if decorate {
		result += fmt.Sprintf("+%s+", RepeatString("-", matrix.Width()*padding))
	}
	result += fmt.Sprintf("%s", lineSeparator)
	return result
}
