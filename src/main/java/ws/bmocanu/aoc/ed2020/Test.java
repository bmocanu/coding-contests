package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.support.FlexNumber;

public class Test {

    public static void main(String[] args) {
        FlexNumber n1 = FlexNumber.fromInt(89);
        System.out.println(n1.printDigits());
        FlexNumber n2 = FlexNumber.fromInt(123456789);
        System.out.println(n2.printDigits());

        n1.add(n2);
        System.out.println(n1.printDigits());

        n1.setDigit(35, 8);
        System.out.println(n1.printDigits());

        n2.setDigit(33, 9);
        System.out.println(n2.printDigits());

        n1.add(n2);
        System.out.println(n1.printDigits());

        n1.add(n1.deepClone());
        System.out.println(n1.printDigits());
        System.out.println(n1.printAsInt());
        System.out.println("---------");

        System.out.println(n1.printDigits());
        System.out.println(n2.printDigits());
        System.out.println(n1.compareTo(n1));
        System.out.println(n1.compareTo(n2));
        System.out.println(n2.compareTo(n1));

    }

}
