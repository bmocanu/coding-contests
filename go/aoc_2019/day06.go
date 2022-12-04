package main

import (
	"../utils"
	"fmt"
	"strings"
)

// Probl 1 result: [270768]
// Probl 2 result: [451]

var nodeGraph = new(utils.UnqNodesGraph)

func main06() {
	var lines = make([]string, 1500)
	var lineCount = utils.ReadFileToStringArray("aoc_2019/day06_input.txt", lines)
	nodeGraph.Init()

	for index := 0; index < lineCount; index++ {
		var currentLine = lines[index]
		var fragments = strings.Split(currentLine, ")")
		nodeGraph.Associate(fragments[0], fragments[1])
	}

	var sum = 0
	for _, node := range nodeGraph.Nodes {
		sum += node.CountParents()
	}
	fmt.Printf("Probl 1 result: [%d]\n", sum)

	var youNode = nodeGraph.GetNode("YOU").Parent
	var youPathCount = 0
	for youNode != nil {
		youNode.IntValue = youPathCount
		youNode = youNode.Parent
		youPathCount++
	}

	var sanNode = nodeGraph.GetNode("SAN").Parent
	var sanPathCount = 0
	for sanNode.IntValue == 0 {
		sanNode = sanNode.Parent
		sanPathCount++
	}

	fmt.Printf("Probl 2 result: [%d]\n", sanNode.IntValue+sanPathCount)
}
