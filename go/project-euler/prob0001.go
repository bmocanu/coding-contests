package main

import "fmt"

/*
https://projecteuler.net/problem=1
*/
func main() {
	var sum = 0
	for nr := 1; nr < 1000; nr++ {
		if nr%3 == 0 || nr%5 == 0 {
			sum += nr
		}
	}

	fmt.Println("Result:", sum) // 233168
}
