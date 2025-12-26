package utils

type LinkedListNode struct {
	Prev        *LinkedListNode
	Next        *LinkedListNode
	Value       int
	Value64     int64
	ValueIntArr []int
	ValueAny    any
}

type LinkedList struct {
	start  *LinkedListNode
	end    *LinkedListNode
	length int
}

// ----------------------------------------------------------------------------------------------------

func (list *LinkedList) Init() *LinkedList {
	list.length = 0
	list.start = nil
	list.end = nil
	return list
}

func (list *LinkedList) First() *LinkedListNode {
	return list.start
}

func (list *LinkedList) Add() *LinkedListNode {
	var newNode = new(LinkedListNode)
	newNode.Prev = list.end
	newNode.Next = nil
	if list.start == nil {
		list.start = newNode
	}
	if list.end != nil {
		list.end.Next = newNode
	}
	list.end = newNode
	list.length++
	return newNode
}

func (list *LinkedList) Remove(node *LinkedListNode) {
	var prevNode = node.Prev
	var nextNode = node.Next
	if prevNode != nil {
		prevNode.Next = nextNode
	}
	if nextNode != nil {
		nextNode.Prev = prevNode
	}
	if list.start == node {
		list.start = nextNode
	}
	if list.end == node {
		list.end = prevNode
	}
	list.length--
}

func (list *LinkedList) RemoveFirst() {
	if list.start == nil {
		panic("Cannot remove the first node as the list is empty")
	}
	list.start = list.start.Next
	if list.start != nil {
		list.start.Prev = nil
	}
	list.length--
}

func (list *LinkedList) Length() int {
	return list.length
}

func (list *LinkedList) NotEmpty() bool {
	return list.length > 0
}
