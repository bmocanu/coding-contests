package ws.bmocanu.aoc.flex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ws.bmocanu.aoc.utils.Utils;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FlexNumber implements Comparable<FlexNumber> {

    private int[] digits = new int[0];
    private int length;

    // ----------------------------------------------------------------------------------------------------

    public static FlexNumber fromInt(int number) {
        FlexNumber result = new FlexNumber();
        result.loadFromInt(number);
        return result;
    }

    public static FlexNumber fromLong(long number) {
        FlexNumber result = new FlexNumber();
        result.loadFromLong(number);
        return result;
    }

    public static FlexNumber fromString(String number) {
        FlexNumber result = new FlexNumber();
        result.loadFromString(number);
        return result;
    }

    // ----------------------------------------------------------------------------------------------------

    public FlexNumber loadFromInt(int number) {
        reset();
        int localValue = number;
        while (localValue > 0) {
            addDigit(localValue % 10);
            localValue = localValue / 10;
        }
        return this;
    }

    public FlexNumber loadFromLong(long number) {
        reset();
        long localValue = number;
        while (localValue > 0) {
            addDigit((int) (localValue % 10));
            localValue = localValue / 10;
        }
        return this;
    }

    public FlexNumber loadFromString(String number) {
        reset();
        String localNumber = number.trim();
        for (int index = number.length() - 1; index >= 0; index--) {
            char currentChar = number.charAt(index);
            if (Utils.charIsDigit(currentChar)) {
                addDigit(currentChar - '0');
            } else {
                throw new IllegalArgumentException("This char is not a digit: " + currentChar);
            }
        }
        return this;
    }

    public FlexNumber loadFromFlexNumber(FlexNumber number) {
        digits = Arrays.copyOf(number.digits, number.digits.length);
        length = number.length;
        return this;
    }

    public int length() {
        return length;
    }

    public FlexNumber reset() {
        digits = new int[0];
        length = 0;
        return this;
    }

    public int[] digits() {
        return digits;
    }

    public FlexNumber addDigit(int digit) {
        makeSureThisIsADigit(digit);
        if (length >= digits.length) {
            expandDigitsArray();
        }
        digits[length] = digit;
        length++;
        return this;
    }

    public FlexNumber deepClone() {
        FlexNumber newNumber = new FlexNumber();
        newNumber.digits = Arrays.copyOf(digits, digits.length);
        newNumber.length = length;
        return newNumber;
    }

    public FlexNumber reverse() {
        for (int index = 0; index < length / 2; index++) {
            int tempDigit = digits[index];
            digits[index] = digits[length - index - 1];
            digits[length - index - 1] = tempDigit;
        }
        return this;
    }

    public String toStringAsInt() {
        StringBuilder builder = new StringBuilder(length);
        for (int index = length - 1; index >= 0; index--) {
            builder.append(digits[index]);
        }
        return builder.toString();
    }

    public String digitsToString() {
        StringBuilder builder = new StringBuilder(digits.length);
        for (int digit : digits) {
            builder.append(digit);
        }
        return builder.toString();
    }

    public FlexNumber add(FlexNumber other) {
        int maxLength = Utils.max(length, other.length);
        int carryOn = 0;
        for (int index = 0; index < maxLength; index++) {
            int term1 = (index < length ? digits[index] : 0);
            int term2 = (index < other.length ? other.digits[index] : 0);
            int result = term1 + term2 + carryOn;
            carryOn = result / 10;
            result = result % 10;
            setDigit(index, result);
        }
        if (carryOn > 0) {
            setDigit(maxLength, carryOn);
        }
        return this;
    }

    public FlexNumber multiply(FlexNumber other) {
        int termCount = 0;
        List<FlexNumber> terms = new ArrayList<>();
        for (int otherIndex = 0; otherIndex < other.length; otherIndex++) {
            FlexNumber newTerm = new FlexNumber();
            if (termCount > 0) {
                newTerm.setDigit(termCount - 1, 0);
            }
            int carryOn = 0;
            for (int thisIndex = 0; thisIndex < length; thisIndex++) {
                int product = digits[thisIndex] * other.digits[otherIndex] + carryOn;
                newTerm.addDigit(product % 10);
                carryOn = product / 10;
            }
            if (carryOn > 0) {
                newTerm.addDigit(carryOn);
            }
            terms.add(newTerm);
            termCount++;
        }
        reset();
        for (FlexNumber term : terms) {
            add(term);
        }
        return this;
    }

    public FlexNumber setDigit(int index, int digit) {
        makeSureThisIsADigit(digit);
        while (index >= digits.length) {
            expandDigitsArray();
        }
        digits[index] = digit;
        if (index >= length) {
            length = index + 1;
        }
        return this;
    }

    @Override
    public int compareTo(FlexNumber other) {
        int result = 0;
        int maxLength = Utils.max(length, other.length);
        for (int index = 0; index < maxLength; index++) {
            int term1 = (index < length ? digits[index] : 0);
            int term2 = (index < other.length ? other.digits[index] : 0);
            if (term1 != term2) {
                result = Integer.compare(term1, term2);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlexNumber)) {
            return false;
        }
        FlexNumber otherObj = (FlexNumber) obj;
        int maxLength = Utils.max(length, otherObj.length);
        for (int index = 0; index < maxLength; index++) {
            int term1 = (index < length ? digits[index] : 0);
            int term2 = (index < otherObj.length ? otherObj.digits[index] : 0);
            if (term1 != term2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digits);
    }

    // ----------------------------------------------------------------------------------------------------

    private void expandDigitsArray() {
        int[] newArray = new int[digits.length * 2 + 1];
        System.arraycopy(digits, 0, newArray, 0, digits.length);
        digits = newArray;
    }

    private void makeSureThisIsADigit(int digit) {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("This is not a digit: " + digit);
        }
    }

}
