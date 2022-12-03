package utils

import (
	"bufio"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

func ReadFileToIntArray(fileName string, array []int) int {
	fileDesc, err := os.Open(fileName)
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
		return 0
	}
	defer fileDesc.Close()

	var numbersLen = 0
	var number int
	for {
		_, err := fmt.Fscanf(fileDesc, "%d\n", &number)
		if err != nil {
			if err == io.EOF {
				return numbersLen
			}
			fmt.Println(err)
			return 0
		}
		array[numbersLen] = number
		numbersLen++
	}
}

func ReadFileToDigitsArray(fileName string) []int {
	var fileContent = ReadFileToString(fileName)
	var resultArray = make([]int, len(fileContent))
	for index := 0; index < len(fileContent); index++ {
		resultArray[index] = int(fileContent[index] - 48)
	}
	return resultArray
}

func ReadFileToString(fileName string) string {
	var fileContent, err = ioutil.ReadFile(fileName)
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
	}
	return string(fileContent)
}

func ReadFileToStringArray(fileName string, array []string) int {
	fileDesc, err := os.Open(fileName)
	if err != nil {
		panic(fmt.Sprintf("Cannot read file: %v", err))
	}
	defer fileDesc.Close()
	var linesLen = 0
	var scanner = bufio.NewScanner(fileDesc)
	for scanner.Scan() {
		array[linesLen] = scanner.Text()
		linesLen++
	}
	err = scanner.Err()
	if err != nil {
		return 0
	}
	return linesLen
}

func SplitCsvStringToIntArray(csvString string, targetArray []int) int {
	var stringSplits = strings.Split(csvString, ",")
	for stringSplitsIndex := range stringSplits {
		var parsedInt, err = strconv.ParseInt(stringSplits[stringSplitsIndex], 10, 0)
		if err != nil {
			fmt.Printf("Failed to parse string to int: [%s]", stringSplits[stringSplitsIndex])
		} else {
			targetArray[stringSplitsIndex] = int(parsedInt)
		}
	}
	return len(stringSplits)
}

func SplitCsvStringToFlexibleArray(csvString string) *FlexArray {
	var stringSplits = strings.Split(csvString, ",")
	var array = new(FlexArray)
	array.Init()
	for stringSplitsIndex := range stringSplits {
		var parsedInt, err = strconv.ParseInt(stringSplits[stringSplitsIndex], 10, 0)
		if err != nil {
			fmt.Printf("Failed to parse string to int: [%s]", stringSplits[stringSplitsIndex])
		} else {
			array.Set(stringSplitsIndex, int(parsedInt))
		}
	}
	return array
}

type stringLineHandler func(string)

func StreamFileAsStringLines(fileName string, handler stringLineHandler) error {
	fileDesc, err := os.Open(fileName)
	if err != nil {
		return err
	}
	defer fileDesc.Close()

	var scanner = bufio.NewScanner(fileDesc)
	for scanner.Scan() {
		handler(scanner.Text())
	}

	err = scanner.Err()
	if err != nil {
		return err
	}

	return nil
}

func ScanString(content string, format string, a ...interface{}) {
	_, err := fmt.Sscanf(content, format, a...)
	if err != nil {
		fmt.Println("Failed to parse string content: "+content, err)
		return
	}
}

func RepeatString(str string, count int) string {
	var result = ""
	for index := 0; index < count; index++ {
		result = result + str
	}
	return result
}

func Spaces(count int) string {
	var result = ""
	for index := 0; index < count; index++ {
		result += " "
	}
	return result
}

func CantorPairingValue(a1 int64, a2 int64) int64 {
	return (a1+a2)*(a1+a2+1)/2 + a2
}

func InitIntMatrix2D(width int, height int, value int) [][]int {
	var matrix = make([][]int, width)
	for x := 0; x < width; x++ {
		matrix[x] = make([]int, height)
		for y := 0; y < height; y++ {
			matrix[x][y] = value
		}
	}
	return matrix
}

func PrintArray(array []int, padding int) {
	fmt.Println()
	fmt.Println("-----------------------------------------")
	for index := 0; index < len(array); index++ {
		fmt.Printf("%"+strconv.Itoa(padding)+"d", array[index])
	}
	fmt.Println()
	fmt.Println("-----------------------------------------")
}

/*
 CharMapping = a CSV of key-Value items, to define the mapping when printing the content
 "0, ,1,#" will result in all zeroes being printed as spaces, and all ones being printed as #
*/
func PrintMatrix(matrix [][]int, charMapping string) {
	fmt.Println("-----------------------------------------")
	var charMap = make(map[int]string)
	if len(charMapping) > 0 {
		var charMappingFragments = strings.Split(charMapping, ",")
		for index := 0; index < len(charMappingFragments); index += 2 {
			var leftValue, _ = strconv.Atoi(charMappingFragments[index])
			charMap[leftValue] = charMappingFragments[index+1]
		}
	}
	for y := 0; y < len(matrix[0]); y++ {
		for x := 0; x < len(matrix); x++ {
			var char, charFound = charMap[matrix[x][y]]
			if charFound {
				fmt.Printf("%s", char)
			} else {
				fmt.Printf("%d", matrix[x][y])
			}
		}
		fmt.Println()
	}
	fmt.Println("-----------------------------------------")
}

func Reverse(s string) string {
	runes := []rune(s)
	for i, j := 0, len(runes)-1; i < j; i, j = i+1, j-1 {
		runes[i], runes[j] = runes[j], runes[i]
	}
	return string(runes)
}
