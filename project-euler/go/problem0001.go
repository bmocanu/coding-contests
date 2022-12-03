package main

import "fmt"

/*
If we list all the natural numbers below 10 that are multiples of 3 or 5,
we get 3, 5, 6 and 9. The sum of these multiples is 23.

Find the sum of all the multiples of 3 or 5 below 1000.
*/

func main1() {
	var sum = 0
	for nr := 1; nr < 1000; nr++ {
		if nr%3 == 0 || nr%5 == 0 {
			sum += nr
		}
	}
	fmt.Printf("Result: %d", sum)
}
