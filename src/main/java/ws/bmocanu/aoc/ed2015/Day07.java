package ws.bmocanu.aoc.ed2015;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.Utils;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

public class Day07 extends SolutionBase {

    static Map<String, Op> regMap = new HashMap<>();
    static Map<String, Integer> regValueMap = new HashMap<>();

    static int getValueForReg(String regName) {
        if (Utils.stringIsNumber(regName)) {
            return Integer.parseInt(regName);
        }
        Integer knownValue = regValueMap.get(regName);
        if (knownValue != null) {
            return knownValue;
        }
        Op regOp = regMap.get(regName);
        int value = regOp.getValue();
        regValueMap.put(regName, value);
        return value;
    }

    public static void main(String[] args) {
        List<String> input = XRead.fileAsStringPerLineToStringList(filePath("day07"));
        for (String line : input) {
            String[] parts = line.split(" ");
            if (parts[0].equals("NOT")) {
                regMap.put(parts[3], new NotOp(parts[1]));
            } else if (parts[1].equals("AND")) {
                regMap.put(parts[4], new AndOp(parts[0], parts[2]));
            } else if (parts[1].equals("OR")) {
                regMap.put(parts[4], new OrOp(parts[0], parts[2]));
            } else if (parts[1].equals("LSHIFT")) {
                regMap.put(parts[4], new LShiftOp(parts[0], parts[2]));
            } else if (parts[1].equals("RSHIFT")) {
                regMap.put(parts[4], new RShiftOp(parts[0], parts[2]));
            } else {
                regMap.put(parts[2], new SetOp(parts[0]));
            }
        }

        int wireA = regMap.get("a").getValue();
        Log.part1(wireA);

        regMap.put("b", new SetOp(wireA + ""));
        regValueMap.clear();
        Log.part1(regMap.get("a").getValue());
    }

    // ----------------------------------------------------------------------------------------------------

    interface Op {
        int getValue();
    }

    static class SetOp implements Op {
        String reg;

        public SetOp(String reg) {
            this.reg = reg;
        }

        public int getValue() {
            return getValueForReg(reg);
        }
    }

    static class AndOp implements Op {
        String reg1;
        String reg2;

        public AndOp(String reg1, String reg2) {
            this.reg1 = reg1;
            this.reg2 = reg2;
        }

        public int getValue() {
            return getValueForReg(reg1) & getValueForReg(reg2);
        }
    }

    static class OrOp implements Op {
        String reg1;
        String reg2;

        public OrOp(String reg1, String reg2) {
            this.reg1 = reg1;
            this.reg2 = reg2;
        }

        public int getValue() {
            return getValueForReg(reg1) | getValueForReg(reg2);
        }
    }

    static class NotOp implements Op {
        String reg;

        public NotOp(String reg) {
            this.reg = reg;
        }

        public int getValue() {
            return ~getValueForReg(reg);
        }
    }

    static class LShiftOp implements Op {
        String reg1;
        String reg2;

        public LShiftOp(String reg1, String reg2) {
            this.reg1 = reg1;
            this.reg2 = reg2;
        }

        public int getValue() {
            return getValueForReg(reg1) << getValueForReg(reg2);
        }
    }

    static class RShiftOp implements Op {
        String reg1;
        String reg2;

        public RShiftOp(String reg1, String reg2) {
            this.reg1 = reg1;
            this.reg2 = reg2;
        }

        public int getValue() {
            return getValueForReg(reg1) >> getValueForReg(reg2);
        }
    }

}
