package main

import (
	utilities "coding-contests/utils"
	"fmt"
	"strings"
)

func main() {
	defer timer("main")()
	var lines [1000]string
	var lineCount = utilities.ReadFileToStringArray("aoc_2025/day11_input.txt", lines[0:])
	var graph = new(utilities.DagGraph)
	graph.Init()

	for index := 0; index < lineCount; index++ {
		var line = lines[index]
		var comps = strings.Split(line, ":")
		var source = strings.TrimSpace(comps[0])
		var destinationList = strings.Fields(comps[1])
		for _, destination := range destinationList {
			graph.AddEdge(source, destination)
		}
	}

	// 649
	var part1Result = calculateTotalPathsForP1(graph, "you", "out")
	fmt.Println("Part1: ", part1Result)

	var part2Result = calculateTotalPathsForP2(graph, "svr", "out", "dac", "fft")
	fmt.Println("Part2: ", part2Result)
}

func calculateTotalPathsForP2(graph *utilities.DagGraph, start string, end string, requiredNode1 string, requiredNode2 string) uint64 {
	graph.BuildTopologicalOrder()
	var startId = graph.GetNodeByName(start).Id
	var endId = graph.GetNodeByName(end).Id
	var req1Id = graph.GetNodeByName(requiredNode1).Id
	var req2Id = graph.GetNodeByName(requiredNode2).Id
	var topoOrder = graph.GetTopologicalOrder()
	// count from start to end in topological order
	var countFromStart = make(map[int]uint64)
	for _, node := range graph.GetNodes() {
		countFromStart[node.Id] = 0
	}
	countFromStart[startId] = 1
	for index := 0; index < len(topoOrder); index++ {
		var nodeId = topoOrder[index]
		var node = graph.GetNodeById(nodeId)
		for _, edge := range node.OutgoingEdges {
			var childNode = edge.ToNode
			countFromStart[childNode.Id] += countFromStart[nodeId]
		}
	}

	// count from end to start in reversed topological order
	var countToEnd = make(map[int]uint64)
	for _, node := range graph.GetNodes() {
		countToEnd[node.Id] = 0
	}
	countToEnd[endId] = 1
	for index := len(topoOrder) - 1; index >= 0; index-- {
		var nodeId = topoOrder[index]
		var node = graph.GetNodeById(nodeId)
		for _, edge := range node.OutgoingEdges {
			var childNode = edge.ToNode
			countToEnd[nodeId] += countToEnd[childNode.Id]
		}
	}

	// create counts for partial traversing, from req1 and req2 nodes
	var countFromReq1To = countFromCustomSource(graph, requiredNode1)
	var countFromReq2To = countFromCustomSource(graph, requiredNode2)

	// final count is:
	// (start => req1) * (req1 => req2) * (reversed count from req2 to end) +
	// (start => req2) * (req2 => req1) * (reversed count from req1 to end)
	var result = countFromStart[req1Id]*countFromReq1To[req2Id]*countToEnd[req2Id] +
		countFromStart[req2Id]*countFromReq2To[req1Id]*countToEnd[req1Id]
	return result
}

func countFromCustomSource(graph *utilities.DagGraph, customSource string) map[int]uint64 {
	var countToSource = make(map[int]uint64)
	for _, node := range graph.GetNodes() {
		countToSource[node.Id] = 0
	}
	countToSource[graph.GetNodeByName(customSource).Id] = 1
	var topoOrder = graph.GetTopologicalOrder()
	for index := 0; index < len(topoOrder); index++ {
		var nodeId = topoOrder[index]
		if countToSource[nodeId] == 0 {
			continue
		}
		var node = graph.GetNodeById(nodeId)
		for _, edge := range node.OutgoingEdges {
			var childNode = edge.ToNode
			countToSource[childNode.Id] += countToSource[nodeId]
		}
	}
	return countToSource
}

func calculateTotalPathsForP1(graph *utilities.DagGraph, startName string, endName string) int {
	graph.ResetValueIntForAllNodes()
	var startNode = graph.GetNodeByName(startName)
	startNode.ValueInt = 1
	var queue = new(utilities.LinkedList)
	queue.Init()
	queue.Add().ValueAny = startNode

	for queue.NotEmpty() {
		var currentNode = queue.First().ValueAny.(*utilities.DagNode)
		queue.RemoveFirst()
		for _, edge := range currentNode.OutgoingEdges {
			var childNode = edge.ToNode
			if childNode.ValueInt == 0 {
				queue.Add().ValueAny = childNode
			}
			childNode.ValueInt += currentNode.ValueInt
		}
	}

	return graph.GetNodeByName(endName).ValueInt
}
