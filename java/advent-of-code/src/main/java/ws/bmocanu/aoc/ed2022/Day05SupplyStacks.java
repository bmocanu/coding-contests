package ws.bmocanu.aoc.ed2022;

import java.util.List;
import java.util.Stack;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SBind;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day05SupplyStacks extends SolutionBase {

    public static class Move {
        int cratesToMove;
        int from;
        int to;
    }

    private static final int TOTAL_STACKS = 9;
    private static final Stack<Character>[] stacks = new Stack[TOTAL_STACKS];

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day05"));
        int movesStartIndex = 0;
        for (int index = 0; index < input.size(); index++) {
            if (input.get(index).trim().length() == 0) {
                movesStartIndex = index + 1;
                break;
            }
        }

        SBind<Move> bind = new SBind("move (\\d+) from (\\d+) to (\\d+)", Move.class, "cratesToMove", "from", "to");
        List<Move> moves = bind.bindAll(input, movesStartIndex);

        for (int part = 1; part <= 2; part++) {
            for (int index = 0; index < stacks.length; index++) {
                stacks[index] = new Stack<>();
            }

            for (int index = movesStartIndex - 3; index >= 0; index--) {
                String line = input.get(index);
                for (int s = 0; s < TOTAL_STACKS; s++) {
                    int createIndex = s * 4 + 1;
                    if (createIndex < line.length() && line.charAt(createIndex) != ' ') {
                        stacks[s].push(line.charAt(createIndex));
                    }
                }
            }

            for (Move move : moves) {
                if (part == 1) {
                    for (int index = 0; index < move.cratesToMove; index++) {
                        stacks[move.to - 1].push(stacks[move.from - 1].pop());
                    }
                } else {
                    Stack<Character> tempStack = new Stack<>();
                    for (int index = 0; index < move.cratesToMove; index++) {
                        tempStack.push(stacks[move.from - 1].pop());
                    }
                    for (int index = 0; index < move.cratesToMove; index++) {
                        stacks[move.to - 1].push(tempStack.pop());
                    }
                }
            }

            StringBuilder builder = new StringBuilder(TOTAL_STACKS);
            for (Stack stack : stacks) {
                builder.append(stack.peek());
            }
            Log.part(part, builder.toString());
        }
    }

}
