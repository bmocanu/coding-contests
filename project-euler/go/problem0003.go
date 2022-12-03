package main

import (
	"fmt"
	"os"
)

/*
The prime factors of 13195 are 5, 7, 13 and 29.
What is the largest prime factor of the number 600851475143 ?
*/

/*
	600851475143 = A * (6K + 1)
	600851475143 = A6K + A
	600851475143 / A / 6 = K
*/

func DoSomt() {

}

func main() {
	var target = 600851475143
	for index := target / 6; index > 2; index-- {
		if (index-1)%6 == 0 && target%index == 0 {
			fmt.Printf("Result: %d", index)
			os.Exit(0)
		}
		if (index+1)%6 == 0 && target%index == 0 {
			fmt.Printf("Result: %d", index)
			os.Exit(0)
		}
		fmt.Println(index)
	}
}
