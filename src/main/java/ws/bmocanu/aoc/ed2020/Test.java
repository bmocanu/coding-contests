package ws.bmocanu.aoc.ed2020;

import ws.bmocanu.aoc.flex.FlexNumber;

public class Test {

    public static void main(String[] args) {
        long n1long = System.currentTimeMillis();
        System.out.println(n1long);
        FlexNumber n1 = FlexNumber.fromLong(n1long);
        FlexNumber n2 = FlexNumber.fromLong(16757485757485L);
        n1.multiply(n2);
        System.out.println(n1.toStringAsInt());

//        FlexNumber n1 = FlexNumber.fromInt(5566);
//        FlexNumber n2 = FlexNumber.fromInt(776);
//        n1.multiply(n2);
//        System.out.println(n1.printAsInt());
    }

}
