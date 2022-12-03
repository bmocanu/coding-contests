package utilities

type Cell struct {
	x     int
	y     int
	value int
}

type FlexibleIntMatrix struct {
	cellMap   map[int]*Cell
	cellCount int
	width     int
	height    int
}

func (matrix *FlexibleIntMatrix) Init() {
	matrix.cellMap = make(map[int]*Cell)
}

func (matrix *FlexibleIntMatrix) Set(x int, y int, value int) {
	var targetCell = matrix.getCell(x, y)
	if targetCell == nil {
		targetCell = new(Cell)
		matrix.cellCount++
		matrix.cellMap[CantorPairingValue(x, y)] = targetCell
		if x >= matrix.width {
			matrix.width = x + 1
		}
		if y >= matrix.height {
			matrix.height = y + 1
		}
	}

	targetCell.value = value
}

func (matrix *FlexibleIntMatrix) Get(x int, y int) int {
	var existingCell = matrix.getCell(x, y)
	if existingCell != nil {
		return existingCell.value
	}
	return 0
}

func (matrix *FlexibleIntMatrix) Size() int {
	return matrix.cellCount
}

func (matrix *FlexibleIntMatrix) Width() int {
	return matrix.width
}

func (matrix *FlexibleIntMatrix) Height() int {
	return matrix.height
}

func (matrix *FlexibleIntMatrix) WidthAndHeight() (int, int) {
	return matrix.width, matrix.height
}

func (matrix *FlexibleIntMatrix) getCell(x int, y int) *Cell {
	var cell, cellFound = matrix.cellMap[CantorPairingValue(x, y)]
	if cellFound {
		return cell
	}
	return nil
}
