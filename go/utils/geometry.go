package utils

import "math"

func PointsOnTheSameLine(p1 *Point, p2 *Point, p3 *Point) bool {
	var p12Distance = Distance(p1, p2)
	var p23Distance = Distance(p2, p3)
	var p13Distance = Distance(p1, p3)
	return math.Abs(p13Distance-(p12Distance+p23Distance)) < 0.00001
}

func Distance(p1 *Point, p2 *Point) float64 {
	var xPart = p1.X - p2.X
	var yPart = p1.Y - p2.Y
	return math.Sqrt(float64(xPart*xPart + yPart*yPart))
}

func RotatePointAroundCenter(angleInDegrees float64, radius int, center *Point, result *Point) {
	var angleInRadians = angleInDegrees * math.Pi / 180
	var sin = math.Sin(angleInRadians)
	var cos = math.Cos(angleInRadians)
	var startX = float64(0)
	var startY = float64(-radius)
	result.X = int(math.Round(startX*cos-startY*sin)) + center.X
	result.Y = int(math.Round(startX*sin+startY*cos)) + center.Y
}

func AngleBetween3PointsInDegrees(p1 *Point, p2 *Point, p3 *Point) float64 {
	var p1X = float64(p1.X)
	var p1Y = float64(p1.Y)
	var p2X = float64(p2.X)
	var p2Y = float64(p2.Y)
	var p3X = float64(p3.X)
	var p3Y = float64(p3.Y)
	var result = math.Atan2(p3Y-p2Y, p3X-p2X) - math.Atan2(p1Y-p2Y, p1X-p2X)
	result = result * 180 / math.Pi
	if result < 0 {
		result = 360 + result
	}
	return result
}
