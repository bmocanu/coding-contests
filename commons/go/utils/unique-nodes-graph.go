package utils

import "fmt"

type UnqNode struct {
	Value         string
	IntValue      int
	Parent        *UnqNode
	ChildrenCount int
	Children      map[string]*UnqNode
}

type UnqNodesGraph struct {
	Nodes      map[string]*UnqNode
	NodesCount int
}

// ----------------------------------------------------------------------------------------------------

func (graph *UnqNodesGraph) Init() {
	graph.Nodes = make(map[string]*UnqNode)
	graph.NodesCount = 0
}

func (graph *UnqNodesGraph) Associate(parent string, child string) {
	var parentNode = graph.AddNode(parent)
	var childNode = graph.AddNode(child)
	if !parentNode.HasChild(child) {
		parentNode.Children[child] = childNode
		parentNode.ChildrenCount++
	}
	if childNode.Parent == nil {
		childNode.Parent = parentNode
	} else {
		fmt.Printf("UnqNodeGraph: Error: node [%s] already has a parent: [%s]\n",
			child, childNode.Parent.Value)
	}
}

func (graph *UnqNodesGraph) AddNode(value string) *UnqNode {
	var theNode = graph.GetNode(value)
	if theNode == nil {
		theNode = new(UnqNode)
		theNode.Value = value
		theNode.Children = make(map[string]*UnqNode)
		theNode.ChildrenCount = 0
		graph.Nodes[value] = theNode
	}
	return theNode
}

func (graph *UnqNodesGraph) GetNode(value string) *UnqNode {
	var node, nodeFound = graph.Nodes[value]
	if !nodeFound {
		return nil
	} else {
		return node
	}
}

// ----------------------------------------------------------------------------------------------------

func (node *UnqNode) HasChild(value string) bool {
	var _, childFound = node.Children[value]
	return childFound
}

func (node *UnqNode) CountParents() int {
	if node.Parent == nil {
		return 0
	} else {
		return node.Parent.CountParents() + 1
	}
}
