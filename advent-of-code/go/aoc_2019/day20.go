package main

import (
	"../utils"
	"fmt"
)

const (
	typeNothing     = 0
	typeWall        = 1
	typePassage     = 2
	typeLetter      = 3
	typeStart       = 4
	typeFinish      = 5
	typePortal      = 6
	typePortalEntry = 7

	locationInside  = 1
	locationOutside = 2
)

func main20() {
	var fileContent = make([]string, 130)
	var lineCount = utils.ReadFileToStringArray("aoc_2019/day20_input_mine.txt", fileContent)

	var initialMtx = new(utils.FlexStruct)
	initialMtx.Init()
	readFileContentToMtx(fileContent, lineCount, initialMtx)
	convertLettersIntoStartFinishAndPortals(initialMtx)

	var mtx = initialMtx.DeepClone()
	mtx.GetFirstPointByTypeAndName(typePortalEntry, "AA").SetType(typePassage).SetValue(1)
	var endEntry = mtx.GetFirstPointByTypeAndName(typePortalEntry, "ZZ").SetType(typePassage)
	var stepNr = 1
	for endEntry.Value == 0 {
		for _, point := range mtx.AllPoints() {
			if point.Value == stepNr {
				if isWalkable(point) {
					for dir := 0; dir < 4; dir++ {
						var nextPoint = mtx.Point(utils.PointCoordsByDelta0123(point, dir))
						if nextPoint.Value == 0 && isWalkable(nextPoint) {
							nextPoint.Value = stepNr + 1
						}
					}
					if point.Type == typePortalEntry {
						var otherPortalEntry = otherPortalEntry(point, mtx)
						if otherPortalEntry.Value == 0 {
							otherPortalEntry.Value = stepNr + 1
						}
					}
				}
			}
		}
		stepNr++
	}

	// mtx.PrintByType("0, ,1,#,2,VAL,3,NME,4,NME,5,NME,6,LOC,7,VAL", 3)
	fmt.Printf("Probl 1 result: %d\n", endEntry.Value-1)

	var levels = make([]*utils.FlexStruct, 50)
	var levelCount = 1
	stepNr = 1
	levels[0] = initialMtx.DeepClone()
	levels[0].GetFirstPointByTypeAndName(typePortalEntry, "AA").SetType(typePassage).SetValue(1)
	endEntry = levels[0].GetFirstPointByTypeAndName(typePortalEntry, "ZZ").SetType(typePassage)
	for endEntry.Value == 0 {
		for levelIndex := 0; levelIndex < levelCount; levelIndex++ {
			for _, point := range levels[levelIndex].AllPoints() {
				if point.Value == stepNr {
					if isWalkable(point) {
						for dir := 0; dir < 4; dir++ {
							var nextPoint = levels[levelIndex].Point(utils.PointCoordsByDelta0123(point, dir))
							if nextPoint.Value == 0 && isWalkable(nextPoint) {
								nextPoint.Value = stepNr + 1
							}
						}
						if point.Type == typePortalEntry {
							if point.Location == locationInside && levelIndex + 1 < len(levels) {
								var otherPortalEntry = otherLevelPortalEntry(point, locationOutside,
									getLevel(levels, levelIndex+1, &levelCount, initialMtx))
								if otherPortalEntry.Value == 0 {
									otherPortalEntry.Value = stepNr + 1
								}
							} else if point.Location == locationOutside && levelIndex > 0 {
								var otherPortalEntry = otherLevelPortalEntry(point, locationInside,
									getLevel(levels, levelIndex-1, &levelCount, initialMtx))
								if otherPortalEntry.Value == 0 {
									otherPortalEntry.Value = stepNr + 1
								}
							}
						}
					}
				}
			}
		}
		stepNr++
	}

	fmt.Printf("Probl 2 result: %d\n", endEntry.Value-1)
}

func readFileContentToMtx(fileContent []string, lineCount int, mtx *utils.FlexStruct) {
	for lineIndex := 0; lineIndex < lineCount; lineIndex++ {
		for x := 0; x < len(fileContent[lineIndex]); x++ {
			switch fileContent[lineIndex][x] {
			case ' ':
				mtx.Point(x, lineIndex).SetType(typeNothing)
			case '#':
				mtx.Point(x, lineIndex).SetType(typeWall)
			case '.':
				mtx.Point(x, lineIndex).SetType(typePassage)
			default:
				if fileContent[lineIndex][x] >= 'A' && fileContent[lineIndex][x] <= 'Z' {
					mtx.Point(x, lineIndex).SetType(typeLetter).SetName(string(fileContent[lineIndex][x]))
				}
			}
		}
		fmt.Println()
	}
}

func convertLettersIntoStartFinishAndPortals(mtx *utils.FlexStruct) {
	var letterPoints = mtx.AllPointsByType(typeLetter)
	for _, point := range letterPoints {
		for dir := 0; dir < 4; dir++ {
			var otherPoint = mtx.Point(utils.PointCoordsByDelta0123(point, dir))
			if otherPoint.Type == typeLetter {
				setLetterConnectionBetweenPoints(
					point, otherPoint,
					mtx.Point(utils.PointCoordsByDelta0123(otherPoint, dir)),
					mtx.Point(utils.PointCoordsByInvDelta0123(point, dir)),
					mtx.Width(),
					mtx.Height())
			}
		}
	}
}

func setLetterConnectionBetweenPoints(p1 *utils.Point, p2 *utils.Point, entry1 *utils.Point, entry2 *utils.Point, mtxWidth int, mtxHeight int) {
	var finalEntry = entry1
	if finalEntry.Type != typePassage {
		finalEntry = entry2
	}
	var portalName = p1.Name + p2.Name
	if utils.PointLeftOfPoint(p2, p1) || utils.PointAbovePoint(p2, p1) {
		portalName = p2.Name + p1.Name
	}
	var portalType = typePortal
	var portalLocation = locationOutside
	if p1.X > 5 && p1.X < mtxWidth-5 && p1.Y > 5 && p1.Y < mtxHeight-5 {
		portalLocation = locationInside
	}
	switch portalName {
	case "AA":
		portalType = typeStart
	case "ZZ":
		portalType = typeFinish
	}
	p1.Type, p1.Name, p1.Link, p1.Location = portalType, portalName, finalEntry, portalLocation
	p2.Type, p2.Name, p2.Link, p2.Location = portalType, portalName, finalEntry, portalLocation
	finalEntry.Type, finalEntry.Name, finalEntry.Location = typePortalEntry, portalName, portalLocation
}

func isWalkable(point *utils.Point) bool {
	return point.Type == typePassage || point.Type == typePortalEntry
}

func otherPortalEntry(portalEntry *utils.Point, mtx *utils.FlexStruct) *utils.Point {
	var portalPoints = mtx.AllPointsByName(portalEntry.Name)
	for _, otherPoint := range portalPoints {
		if otherPoint.Type == typePortal && otherPoint.Link != portalEntry {
			return otherPoint.Link
		}
	}
	fmt.Printf("Error: cannot find other portal entry for %s\n", portalEntry.Name)
	return nil
}

func otherLevelPortalEntry(portalEntry *utils.Point, locationType int, level *utils.FlexStruct) *utils.Point {
	var portalPoints = level.AllPointsByName(portalEntry.Name)
	for _, otherPoint := range portalPoints {
		if otherPoint.Type == typePortal && otherPoint.Link != portalEntry && otherPoint.Location == locationType {
			return otherPoint.Link
		}
	}
	fmt.Printf("Error: cannot find other portal entry for %s\n", portalEntry.Name)
	return nil
}

func getLevel(levels []*utils.FlexStruct, levelIndex int, levelCount *int, initialMtx *utils.FlexStruct) *utils.FlexStruct {
	if levelIndex >= *levelCount {
		*levelCount++
		levels[levelIndex] = initialMtx.DeepClone()
		levels[levelIndex].GetFirstPointByTypeAndName(typePortalEntry, "AA").SetType(typePassage)
		levels[levelIndex].GetFirstPointByTypeAndName(typePortalEntry, "ZZ").SetType(typePassage)
	}
	return levels[levelIndex]
}
