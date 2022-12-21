package ws.bmocanu.aoc.ed2022;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21MonkeyMath extends SolutionBase {

    private static final Map<String, Node> nodeMap = new HashMap<>();

    private static class Node {
        String name;
        String operation;
        Node left;
        Node right;
        long value;

        public long evaluate() {
            switch (operation) {
                case "val":
                    return value;
                case "+":
                    return left.evaluate() + right.evaluate();
                case "-":
                    return left.evaluate() - right.evaluate();
                case "/":
                    return left.evaluate() / right.evaluate();
                case "*":
                    return left.evaluate() * right.evaluate();
                default:
                    throw new IllegalArgumentException("Invalid operation=" + operation);
            }
        }

        @SuppressWarnings("unused")
        public String print() {
            switch (operation) {
                case "val":
                    return (name.equals("humn") ? "x" : "" + value);
                case "+":
                    return "(" + left.print() + "+" + right.print() + ")";
                case "-":
                    return "(" + left.print() + "-" + right.print() + ")";
                case "/":
                    return "(" + left.print() + "/" + right.print() + ")";
                case "*":
                    return "(" + left.print() + "*" + right.print() + ")";
                default:
                    throw new IllegalArgumentException("Invalid operation=" + operation);
            }
        }

    }

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day21"));
        for (String line : input) {
            String[] nameComps = line.split(": ");
            String name = nameComps[0];
            String operation = null;
            String left = null;
            String right = null;
            long value = 0;
            if (nameComps[1].contains(" + ")) {
                String[] elemComps = nameComps[1].split(" \\+ ");
                operation = "+";
                left = elemComps[0];
                right = elemComps[1];
            } else if (nameComps[1].contains(" - ")) {
                String[] elemComps = nameComps[1].split(" - ");
                operation = "-";
                left = elemComps[0];
                right = elemComps[1];
            } else if (nameComps[1].contains(" / ")) {
                String[] elemComps = nameComps[1].split(" / ");
                operation = "/";
                left = elemComps[0];
                right = elemComps[1];
            } else if (nameComps[1].contains(" * ")) {
                String[] elemComps = nameComps[1].split(" \\* ");
                operation = "*";
                left = elemComps[0];
                right = elemComps[1];
            } else {
                operation = "val";
                value = Integer.parseInt(nameComps[1]);
            }
            Node node = getNode(name);
            node.name = name;
            node.value = value;
            node.operation = operation;
            node.left = getNode(left);
            node.right = getNode(right);
        }

        Node root = getNode("root");
        Log.part1(root.evaluate());

        // For part 2, for the sample input brute force can be used:
        Node human = getNode("humn");
        for (long humanNr = 0; humanNr < 10000; humanNr++) {
            human.value = humanNr;
            if (root.left.evaluate() == root.right.evaluate()) {
                Log.part2(humanNr);
                return;
            }
        }

        // For my input, the brute force does not work
        // Therefore, I used the Node.print() to print the left part of the equation

        // System.out.println(root.left.print());

        // The right part of root is a constant 37175119093215L

        // Therefore, I printed the <left part containing an X> = 37175119093215L
        // Then I solved the equation online, e.g. https://quickmath.com
        // The complete equation is below
    }

    private static Node getNode(String name) {
        Node node = nodeMap.get(name);
        if (node == null) {
            node = new Node();
            nodeMap.put(name, node);
        }
        return node;
    }

    // ((((((((((((((3*2)+5)+2)+6)+20)*3)-(2*17))*((12*10)+(2+(((4+13)*5)-(13*2)))))*(((17*2)-3)*(((7+(20/2))*(6+((7+(3*2))*5)))+(2*(5*(((3*(13*2))+(5*13))+((8*(14+9))+4)))))))+((((((((3*(2+5))*7)-(((2*5)*((6+1)+1))/2))*((((10+(10+3))+((3*2)*3))-4)*((2*3)+(2+5))))+(((12*(1+((3*3)-3)))+(((7*11)*2)+(((3*(10+7))+10)+((6*(3*2))/2))))*(7*11)))+(((((2*(1+(2*3)))/2)*(14-3))*(((2*((((((2*3)*14)/4)*((3*2)*2))+((17*2)*(((((4*2)*4)-((2*4)+1))*2)-(3*5))))+(9*((7+4)+(7*2)))))/2)*5))/(1+6)))*3)*(5*(((((((((14*(3+4))+(16*8))/2)*2)/2)*2)/2)+(2*(3*3)))+((((3*4)+((2*3)+5))*2)*3)))))+((((3*(5*5))+(((11*4)+(5*(1+12)))*2))*(9*17))*((((5+(4*2))+18)-2)*((5+(((((((((((2*10)+3)*2)-(5*3))-(2*4))+15)/2)*3)+(8*4))-2)*2))*3))))*((((2*((1+18)+(6*10)))+(3*3))*(((2*((3+((7*2)*2))*2))/4)+(5*2)))-((((3*((3*((5+10)+(2*16)))/3))+(((((2*(9+8))-5)*2)/2)*2))*3)*3)))-(((((((((((((5*2)+(10+(3*(((5+3)+5)-2))))*((3+4)*2))+(((13+4)*((2*3)*4))+(((3*3)*((((((2*((((9*((2*(8+(3*5)))+(11*3)))+(((((2+((20-1)*3))*2)+((((2*(((((4*2)+5)*5)+4)+10))/2)+((5*2)*(4+3)))*2))+((7+((((1+(2*(11*5)))/3)+14)*2))+((((2*(((6+(5*4))+8)+((2+7)-2)))*2)/2)+(4*17))))+(2*(((((((((((4*3)+5)*13)-((3+(4*17))-1))-(11-3))+(((7*4)+(3*(1+(3*2))))*(2+5)))+((((((((((6*2)+2)*2)+(((2+11)*2)+(2+15)))*3)*3)+(((((5*(((5+2)+4)+2))+((3*((6+(5+6))*2))+(((2*19)+(7*3))+2)))+(2*((5*5)+(2*(3*7)))))+(((18*(((5*((2*((((((((((4+2)+5)+(2*4))+12)*2)*2)+10)/2)*2)/2))-((15+11)+11)))*2)+(((((2*(((((((2+(5*7))*20)+(((((((2*4)+3)*((((16+8)-((3*(4*4))/8))*(3*((3*2)+(5+1))))+(((((2*((((((((2*(((2*3)+1)+4))+9)+9)-11)*5)/5)*2)/2))-(6+(3+(3*2))))*((4*4)+1))+(((2*5)*(((((((((2+5)*5)*(4*4))+(((((x-((((((2*6)+(4+13))*6)-((19*3)-(4+(5*2))))*2)-11))*16)+(((4*((14-3)+((4+2)*3)))+(((((((19+(3*9))-11)*3)+((8*11)/8))*2)-((13+6)*3))-((1+18)*2)))+(2*((((18*5)/3)+13)+(2*((2*14)+((3+4)+(3*4))))))))+(((7*19)+(3*((2*(((2*(19*2))+8)-(17-4)))+(3*((2*(1+(17+(9+2))))/2)))))+(((2+5)+(2+14))*4)))+(2*((9*3)+((4*4)*20)))))+(2*((17*7)+(11*10))))/3)+(((2*(2*((((2+6)+15)*(2*(2+(3*3))))+((3*3)+(11*12)))))/2)/2))/2)-((5*(((2*20)+1)*2))+((((((((2*16)-(3*3))*2)/2)*2)/2)-(4+2))*(2*5)))))-((1+(4*7))*((4+9)-4))))/(2*5))))+(2*(((((5*4)+(5+(9*2)))+16)*2)+(((1+((3+(((1+10)+5)+7))+4))*3)+(2*(3+20))))))/3)-(((((5*5)+4)*2)*((7+2)+1))+((3*((4*2)+1))+((((5*5)-2)-(2*3))*2))))*3))/2)-(((2*11)+1)*5))*2)+(((8+5)+(2*((((2*(5*(1+(2*3))))/2)+(((2*5)+(((3+(5*2))+9)/2))+8))-13)))*5)))-(13+((2+((2*(((10+13)+((3*7)*6))-(10+2)))/2))*3)))/4)-(((2+5)+(9+(17*2)))+(3*(3*9))))/7)))-(2*(((18+5)+(((2*((2*5)-1))+5)*2))+(5*2))))/4))*2))/3)-(((2*((5+((((2*5)+9)*2)/2))-5))/2)*((5*2)-3)))*3))*2)-(((7+2)+(4+((2+5)-1)))*2))/2)-(((2+(3*(5+2)))*(16+3))+(19*((7*2)+9)))))))/2)-(3*((((((9+1)+20)/5)+(((3*2)+5)*3))*2)+((6+5)*(((4*8)/4)+3))))))+((((2*16)+(((6+(5+1))-1)*3))*5)-((3+(2*13))+(4*11))))/2)+(((4+7)*2)*(6*((2+4)+1))))/5)-((2*12)*(2+5))))+(2*(((1+(2*6))*(4+7))+((((13+6)*2)+(2*(8+(7*2))))+((3+10)*2)))))))/11)-((((((6+((((8*2)+7)*2)/2))*3)*4)/4)/3)*(2*((3*3)+4))))*2)+(((3*((3*(4*3))+11))+16)*3))/3)-(15*((3*9)+5)))*9)+((17*(4*4))+(((14+5)*7)*2)))/5))*(8+1))=37175119093215

}
