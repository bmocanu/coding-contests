package main

import "fmt"

func main04() {
	var passCount1 = 0
	var passCount2 = 0
	for index := 147981; index <= 691423; index++ {
		if checkPasswordRules(index, false) {
			passCount1++
		}
		if checkPasswordRules(index, true) {
			passCount2++
		}
	}

	fmt.Printf("Probl 1 result: %d\n", passCount1)
	fmt.Printf("Probl 2 result: %d\n", passCount2)
}

func checkPasswordRules(pass int, checkDuplicatesCount bool) bool {
	var previousDigit = pass % 10
	var minDuplicatedDigits = 10
	var countDuplicatedDigits = 1
	for pass > 0 {
		pass = pass / 10
		var currentDigit = pass % 10
		if currentDigit == previousDigit {
			countDuplicatedDigits++
		} else {
			if countDuplicatedDigits > 1 {
				if countDuplicatedDigits < minDuplicatedDigits {
					minDuplicatedDigits = countDuplicatedDigits
				}
				countDuplicatedDigits = 1
			}
		}
		if currentDigit > previousDigit {
			return false
		}
		previousDigit = currentDigit
	}
	if checkDuplicatesCount {
		return minDuplicatedDigits == 2
	} else {
		return minDuplicatedDigits < 10
	}
}
