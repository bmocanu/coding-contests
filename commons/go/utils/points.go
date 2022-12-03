package utils

import "fmt"

type Point struct {
	X              int
	Y              int
	Value          int
	Name           string
	Destroyed      bool
	Enabled        bool
	Marked         bool
	TrailMarkCount int
	Type           int
	Location       int
	Link           *Point
}

type Point3D struct {
	X         int
	Y         int
	Z         int
	Value     int
	Destroyed bool
	Enabled   bool
	Marked    bool
}

// ----------------------------------------------------------------------------------------------------

func TaxicabDistance(x1 int, y1 int, x2 int, y2 int) int {
	return Abs(x1-x2) + Abs(y1-y2)
}

func TaxicabDistanceByPoints(p1 *Point, p2 *Point) int {
	return TaxicabDistance(p1.X, p1.Y, p2.X, p2.Y)
}

func GetDirectionDeltaByStringUDLR(command string) (int, int) {
	var dirX = 0
	var dirY = 0
	switch command {
	case "U":
		dirY = -1
	case "R":
		dirX = 1
	case "D":
		dirY = 1
	case "L":
		dirX = -1
	default:
		fmt.Printf("Error: invalid command for delta direction [%s]", command)
	}
	return dirX, dirY
}

func DirectionDelta0123(command int) (int, int) {
	var dirX = 0
	var dirY = 0
	switch command {
	case 0:
		dirY = -1
	case 1:
		dirX = 1
	case 2:
		dirY = 1
	case 3:
		dirX = -1
	default:
		fmt.Printf("Error: invalid command for delta direction [%d]", command)
	}
	return dirX, dirY
}

func DirectionDelta0to7(dir int) (int, int) {
	var dirX = 0
	var dirY = 0
	switch dir {
	case 0:
		dirX = -1
		dirY = -1
	case 1:
		dirX = 0
		dirY = -1
	case 2:
		dirX = 1
		dirY = -1
	case 3:
		dirX = 1
		dirY = 0
	case 4:
		dirX = 1
		dirY = 1
	case 5:
		dirX = 0
		dirY = 1
	case 6:
		dirX = -1
		dirY = 1
	case 7:
		dirX = -1
		dirY = 0
	default:
		fmt.Printf("Error: invalid dir for delta direction [%d]", dir)
	}
	return dirX, dirY
}

func InvDirectionDelta0123(dir int) (int, int) {
	var dirX = 0
	var dirY = 0
	switch dir {
	case 0:
		dirY = 1
	case 1:
		dirX = -1
	case 2:
		dirY = -1
	case 3:
		dirX = 1
	default:
		fmt.Printf("Error: invalid command for inversed delta direction [%d]", dir)
	}
	return dirX, dirY
}

func AdjustByDirectionDelta0123(posX int, posY int, dir int) (int, int) {
	var deltaX, deltaY = DirectionDelta0123(dir)
	return posX + deltaX, posY + deltaY
}

func PointCoordsByDelta0123(point *Point, dir int) (int, int) {
	var deltaX, deltaY = DirectionDelta0123(dir)
	return point.X + deltaX, point.Y + deltaY
}

func PointCoordsByInvDelta0123(point *Point, dir int) (int, int) {
	var deltaX, deltaY = InvDirectionDelta0123(dir)
	return point.X + deltaX, point.Y + deltaY
}

func PointCoordsByDelta0to7(point *Point, dir int) (int, int) {
	var deltaX, deltaY = DirectionDelta0to7(dir)
	return point.X + deltaX, point.Y + deltaY
}

func AdjustByInvDirectionDelta0123(posX int, posY int, dir int) (int, int) {
	var deltaX, deltaY = InvDirectionDelta0123(dir)
	return posX + deltaX, posY + deltaY
}

func NewPoint(x int, y int) *Point {
	var newPoint = new(Point)
	newPoint.X = x
	newPoint.Y = y
	return newPoint
}

func AdjustPoint3DByDelta(point *Point3D, deltaX int, deltaY int, deltaZ int) {
	point.X += deltaX
	point.Y += deltaY
	point.Z += deltaZ
}

func AddPoint3DByPoint3D(p1 *Point3D, p2 *Point3D) {
	p1.X += p2.X
	p1.Y += p2.Y
	p1.Z += p2.Z
}

func PointLeftOfPoint(p1 *Point, p2 *Point) bool {
	return p1.X < p2.X && p1.Y == p2.Y
}

func PointAbovePoint(p1 *Point, p2 *Point) bool {
	return p1.X == p2.X && p1.Y < p2.Y
}

func SumOfAbsCoordinatesOfPoint3D(point *Point3D) int {
	return Abs(point.X) + Abs(point.Y) + Abs(point.Z)
}

func CantorPairingForPoint3D(point *Point3D) int64 {
	var positivityDelta = 30
	var firstPairing = CantorPairingValue(int64(point.X+positivityDelta), int64(point.Y+positivityDelta))
	return CantorPairingValue(firstPairing, int64(point.Y+positivityDelta))
}

func ZeroThesePoints3D(points ...*Point3D) {
	for index := 0; index < len(points); index++ {
		points[index].X, points[index].Y, points[index].Z = 0, 0, 0
	}
}

/*
 Returns an array of each specific coordinate of each Point3D.
 coord = 0 for X, 1 for Y, 2 for Z
*/
func ArrayOfSingleCoordOfPoints3D(points []*Point3D, coord int) []int {
	var result = make([]int, len(points))
	for index := 0; index < len(result); index++ {
		switch coord {
		case 0:
			result[index] = points[index].X
		case 1:
			result[index] = points[index].Y
		case 2:
			result[index] = points[index].Z
		}
	}
	return result
}

func (point *Point) Mark() *Point {
	point.Marked = true
	return point
}

func (point *Point) Destroy() *Point {
	point.Destroyed = true
	return point
}

func (point *Point) Enable() *Point {
	point.Enabled = true
	return point
}

func (point *Point) Disable() *Point {
	point.Enabled = false
	return point
}

func (point *Point) TrailMark() *Point {
	point.TrailMarkCount++
	return point
}

func (point *Point) SetType(newType int) *Point {
	point.Type = newType
	return point
}

func (point *Point) SetLocation(newLocation int) *Point {
	point.Location = newLocation
	return point
}

func (point *Point) SetValue(value int) *Point {
	point.Value = value
	return point
}

func (point *Point) SetName(value string) *Point {
	point.Name = value
	return point
}

func (point *Point) SetLink(link *Point) *Point {
	point.Link = link
	return point
}

func (point *Point) SetValueIfZero(value int) *Point {
	if point.Value == 0 {
		point.Value = value
	}
	return point
}
