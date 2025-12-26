package main

import "fmt"

func main() {
    var numberN = 100000
    for nr1 := 1; nr1 < numberN; nr1++ {
        if isPalindrome(nr1, nr1, 0) {
            for nr2 := nr1 + 1; nr2 <= numberN-nr1; nr2++ {
                if isPalindrome(nr2, nr2, 0) {
                    for nr3 := nr2 + 1; nr3 <= numberN-nr1-nr2; nr3++ {
                        if isPalindrome(nr3, nr3, 0) {
                            if nr1+nr2+nr3 == numberN {
                                fmt.Printf("Number %d can be split in this sum of palindromes: %d + %d + %d\n",
                                    numberN, nr1, nr2, nr3)
                            }

                        }
                    }
                }
            }
        }
    }
}

func isPalindrome(nrToTest int, currentNr int, reversedNr int) bool {
    if currentNr == 0 {
        return nrToTest == reversedNr
    }
    return isPalindrome(nrToTest, currentNr/10, reversedNr*10+currentNr%10)
}
