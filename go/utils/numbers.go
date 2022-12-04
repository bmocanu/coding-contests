package utils

import "math"

func PrimeFactors(value int) map[int]int {
	var factorMap = make(map[int]int)
	var remainder = value
	var divisorFound = true
	for remainder > 1 && divisorFound {
		divisorFound = false
		for div := 2; div <= remainder && !divisorFound; div++ {
			if remainder%div == 0 {
				var factorRepetition, found = factorMap[div]
				if !found {
					factorMap[div] = 1
				} else {
					factorMap[div] = factorRepetition + 1
				}
				remainder = remainder / div
				divisorFound = true
			}
		}

	}
	if len(factorMap) == 0 {
		factorMap[value] = 1
	}
	return factorMap
}

func SmallestCommonMultiplier(values []int) int {
	var finalMap = make(map[int]int)
	for index := 0; index < len(values); index++ {
		var currentMap = PrimeFactors(values[index])
		for factor, repetition := range currentMap {
			var finalRepetition, found = finalMap[factor]
			if !found {
				finalMap[factor] = repetition
			} else {
				finalMap[factor] = Max(finalRepetition, repetition)
			}
		}
	}
	var result = 1
	for factor, repetition := range finalMap {
		result *= Pow(factor, repetition)
	}
	return result
}

func Max(v1 int, v2 int) int {
	if v1 < v2 {
		return v2
	}
	return v1
}

func Pow(x int, power int) int {
	var result = 1
	for index := 0; index < power; index++ {
		result *= x
	}
	return result
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

func CycleInt(value int, minValue int, maxValue int) int {
	if value < minValue {
		return maxValue
	} else if value > maxValue {
		return minValue
	} else {
		return value
	}
}

func CompareInt(val1 int, val2 int) int {
	if val1 < val2 {
		return -1
	} else if val1 > val2 {
		return 1
	} else {
		return 0
	}
}

func CompareInt64(val1 int64, val2 int64) int64 {
	if val1 < val2 {
		return -1
	} else if val1 > val2 {
		return 1
	} else {
		return 0
	}
}

func ArrayToNumber(array []int) int {
	var result = 0
	var multiplier = 1
	for index := len(array) - 1; index >= 0; index-- {
		result += array[index] * multiplier
		multiplier *= 10
	}
	return result
}
