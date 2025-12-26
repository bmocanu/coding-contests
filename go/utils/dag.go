package utils

type DagNode struct {
	Id            int
	IncomingEdges []*DagEdge
	OutgoingEdges []*DagEdge
	Name          string
	ValueStr      string
	ValueInt      int
	ValueUint64   uint64
}

type DagEdge struct {
	FromNode *DagNode
	ToNode   *DagNode
	ValueInt int
	ValueStr string
}

type DagGraph struct {
	nodesNameMap     map[string]*DagNode
	nodesIdMap       map[int]*DagNode
	nodes            []*DagNode
	edges            []*DagEdge
	nextNodeId       int
	topologicalOrder []int
}

// ----------------------------------------------------------------------------------------------------

func (graph *DagGraph) Init() {
	graph.nodesNameMap = make(map[string]*DagNode)
	graph.nodesIdMap = make(map[int]*DagNode)
	graph.nodes = make([]*DagNode, 0)
	graph.edges = make([]*DagEdge, 0)
	graph.nextNodeId = 1
}

func (graph *DagGraph) ResetValueIntForAllNodes() {
	for _, node := range graph.nodes {
		node.ValueInt = 0
	}
}

func (graph *DagGraph) AddEdge(fromNodeName string, toNodeName string) *DagEdge {
	var fromNode = graph.GetOrAddNode(fromNodeName)
	var toNode = graph.GetOrAddNode(toNodeName)
	// check that the edge does not already exist
	var newEdge = new(DagEdge)
	newEdge.FromNode = fromNode
	newEdge.ToNode = toNode
	fromNode.OutgoingEdges = append(fromNode.OutgoingEdges, newEdge)
	toNode.IncomingEdges = append(toNode.IncomingEdges, newEdge)
	graph.edges = append(graph.edges, newEdge)
	return newEdge
}

func (graph *DagGraph) GetOrAddNode(nodeName string) *DagNode {
	var node, exists = graph.nodesNameMap[nodeName]
	if !exists {
		node = new(DagNode)
		node.Id = graph.nextNodeId
		node.Name = nodeName
		node.IncomingEdges = make([]*DagEdge, 0)
		node.OutgoingEdges = make([]*DagEdge, 0)
		graph.nodesIdMap[graph.nextNodeId] = node
		graph.nodesNameMap[nodeName] = node
		graph.nodes = append(graph.nodes, node)
		graph.nextNodeId++
	}
	return node
}

func (graph *DagGraph) GetNodeByName(nodeName string) *DagNode {
	var node, nodeFound = graph.nodesNameMap[nodeName]
	if !nodeFound {
		return nil
	}
	return node
}

func (graph *DagGraph) GetNodeById(nodeId int) *DagNode {
	var node, nodeFound = graph.nodesIdMap[nodeId]
	if !nodeFound {
		return nil
	}
	return node
}

func (graph *DagGraph) GetNodes() []*DagNode {
	return graph.nodes
}

// BuildTopologicalOrder implements Khan's algorithm for topological order
func (graph *DagGraph) BuildTopologicalOrder() {
	graph.topologicalOrder = make([]int, 0)
	var queue = make([]int, 0)
	var inDegrees = make(map[int]int)
	for _, node := range graph.nodes {
		inDegrees[node.Id] = len(node.IncomingEdges)
		if inDegrees[node.Id] == 0 {
			queue = append(queue, node.Id)
		}
	}
	for len(queue) > 0 {
		var nodeId = queue[0]
		queue = queue[1:]
		graph.topologicalOrder = append(graph.topologicalOrder, nodeId)
		var node = graph.nodesIdMap[nodeId]
		for _, outgoingEdge := range node.OutgoingEdges {
			inDegrees[outgoingEdge.ToNode.Id]--
			if inDegrees[outgoingEdge.ToNode.Id] == 0 {
				queue = append(queue, outgoingEdge.ToNode.Id)
			}
		}
	}
}

func (graph *DagGraph) GetTopologicalOrder() []int {
	return graph.topologicalOrder
}
