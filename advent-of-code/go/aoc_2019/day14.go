package main

import (
	"../utils"
	"fmt"
	"strconv"
	"strings"
)

type elementType struct {
	name   string
	amount int
}

type reactionType struct {
	resultElement *elementType
	needs         []*elementType
}

var reactionList []*reactionType

func main14() {
	var lines = make([]string, 64)
	var lineCount = utils.ReadFileToStringArray("aoc_2019/day14_input3.txt", lines)
	reactionList = make([]*reactionType, lineCount)
	for lineIndex := 0; lineIndex < lineCount; lineIndex++ {
		var currentReaction = parseReactionLine(lines[lineIndex])
		reactionList[lineIndex] = currentReaction
	}

	var minOreMap = make(map[string]int)
	calculateMinOreFor("FUEL", 1, &minOreMap, 0)
	fmt.Printf("Probl 1 result: %d\n", minOreMap["ORE"])

	for finalFuel := 3200000; finalFuel < 4000000; finalFuel += 1 {
		var maxOreMap = make(map[string]int)
		calculateMinOreFor("FUEL", finalFuel, &maxOreMap, 0)
		var neededOre = maxOreMap["ORE"]
		fmt.Printf("%d\n", neededOre)
		if neededOre >= 1000000000000 {
			fmt.Printf("Probl 2 result: %d\n", finalFuel)
			break
		}
	}
}

func calculateMinOreFor(elemNeeded string, amountNeeded int, costMap *map[string]int, level int) {
	addAmountToCostMap(elemNeeded, amountNeeded, costMap)
	var matchingReactionCount = 0
	for _, reaction := range reactionList {
		if reaction.resultElement.name == elemNeeded {
			matchingReactionCount++
			var localMultiplier = calculateMultiplier(amountNeeded, reaction.resultElement.amount)
			var resultAmount = reaction.resultElement.amount * localMultiplier
			if resultAmount > amountNeeded {
				addResidueToCostMap(elemNeeded, resultAmount-amountNeeded, costMap)
			}
			for _, reactionNeed := range reaction.needs {
				var reactionNeedAmount = reactionNeed.amount * localMultiplier
				reactionNeedAmount = consumeResidueFromCostMap(reactionNeed.name, reactionNeedAmount, costMap)
				if reactionNeedAmount > 0 {
					calculateMinOreFor(reactionNeed.name, reactionNeedAmount, costMap, level+1)
				}
			}
		}
	}
	if matchingReactionCount > 1 {
		fmt.Printf("!!!!!!!!!!!!!!! Found more than 1 reaction for %s\n", elemNeeded)
	} else
	if matchingReactionCount <= 0 && elemNeeded != "ORE" {
		fmt.Printf("!!!!!!!!!!!!!!! Found no reaction for %s\n", elemNeeded)
	}
}

func addAmountToCostMap(element string, amount int, costMap *map[string]int) {
	var existingAmount, _ = (*costMap)[element]
	(*costMap)[element] = existingAmount + amount
}

func addResidueToCostMap(element string, amount int, costMap *map[string]int) {
	var existingAmount, _ = (*costMap)["_"+element]
	(*costMap)["_"+element] = existingAmount + amount
}

func consumeResidueFromCostMap(element string, howMuch int, costMap *map[string]int) int {
	var existingAmount, _ = (*costMap)["_"+element]
	var result = existingAmount - howMuch
	if result > 0 {
		(*costMap)["_"+element] = result
		return 0
	} else {
		(*costMap)["_"+element] = 0
		return -result
	}
}

func parseReactionLine(line string) (result *reactionType) {
	result = new(reactionType)
	// e.g. 5 JDJB, 1 FSWFT, 1 NKVSV => 6 MGKSL
	var firstSplit = strings.Split(line, "=>")
	var secondSplit = strings.Split(strings.TrimSpace(firstSplit[0]), ",")
	result.resultElement = parseElementString(firstSplit[1])
	result.needs = make([]*elementType, len(secondSplit))
	for index, split := range secondSplit {
		result.needs[index] = parseElementString(split)
	}
	return
}

func parseElementString(str string) (result *elementType) {
	// 6 MGKSL
	var split = strings.Split(strings.TrimSpace(str), " ")
	result = new(elementType)
	result.name = split[1]
	result.amount, _ = strconv.Atoi(split[0])
	return
}

func calculateMultiplier(howMuchIsNeeded int, howMuchIsProduced int) int {
	var result = howMuchIsNeeded / howMuchIsProduced
	if howMuchIsNeeded%howMuchIsProduced > 0 {
		result++
	}
	return result
}
