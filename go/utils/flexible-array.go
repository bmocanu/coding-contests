package utils

type FlexArray struct {
	fStruct *FlexStruct
}

// ----------------------------------------------------------------------------------------------------

func (array *FlexArray) Init() {
	array.fStruct = new(FlexStruct)
	array.fStruct.Init()
}

func (array *FlexArray) Set(index int, value int) {
	array.fStruct.SetInt(index, 0, value)
}

func (array *FlexArray) Get(index int) int {
	return array.fStruct.GetInt(index, 0)
}

func (array *FlexArray) Size() int {
	return array.fStruct.Width()
}

func (array *FlexArray) DeepClone() *FlexArray {
	var newArray = new(FlexArray)
	newArray.fStruct = array.fStruct.DeepClone()
	return newArray
}

func (array *FlexArray) Print() {
	array.fStruct.Print()
}
