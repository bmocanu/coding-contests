package utilities

import (
	"bufio"
	"fmt"
	"io"
	"io/ioutil"
	"math"
	"os"
	"strconv"
	"strings"
)

func ReadFileToIntArray(fileName string, array []int) (int, error) {
	fileDesc, err := os.Open(fileName)
	if err != nil {
		return 0, err
	}
	defer fileDesc.Close()

	var numbersLen = 0
	var number int
	for {
		_, err := fmt.Fscanf(fileDesc, "%d\n", &number)
		if err != nil {
			if err == io.EOF {
				return numbersLen, nil
			}
			fmt.Println(err)
			return 0, err
		}
		array[numbersLen] = number
		numbersLen++
	}
}

func ReadFileToString(fileName string) (string, error) {
	var fileContent, err = ioutil.ReadFile(fileName)
	if err != nil {
		return "", err
	}
	return string(fileContent), nil
}

func ReadFileToStringArray(fileName string, array []string) (int, error) {
	fileDesc, err := os.Open(fileName)
	if err != nil {
		return 0, err
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
		return 0, err
	}

	return linesLen, nil
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

func Max(v1 int, v2 int) int {
	if v1 < v2 {
		return v2
	}
	return v1
}

func Min(v1 int, v2 int) int {
	if v1 < v2 {
		return v1
	}
	return v2
}

func MaxValue(array []int) int {
	var maxValue = math.MinInt32
	for index := 0; index < len(array); index++ {
		if array[index] > maxValue {
			maxValue = array[index]
		}
	}

	return maxValue
}

func Abs(value int) int {
	if value < 0 {
		return -value
	}
	return value
}

func RepeatString(str string, count int) string {
	var result = ""
	for index := 0; index < count; index++ {
		result = result + str
	}
	return result
}

func TaxicabDistance(x1 int, y1 int, x2 int, y2 int) int {
	return Abs(x1-x2) + Abs(y1-y2)
}

func CantorPairingValue(a1 int, a2 int) int {
	return (a1+a2)*(a1+a2+1)/2 + a2
}

func GetDirDeltaByStringUDLR(command string) (int, int) {
	var dirX = 0
	var dirY = 0
	switch command {
	case "L":
		dirX = -1
	case "R":
		dirX = 1
	case "U":
		dirY = -1
	case "D":
		dirY = 1
	default:
		fmt.Printf("Error: invalid command for delta direction [%s]", command)
	}
	return dirX, dirY
}
