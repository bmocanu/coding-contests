package ws.bmocanu.aoc.ed2020;

import java.util.ArrayList;
import java.util.List;

import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.support.SBinder;
import ws.bmocanu.aoc.utils.FileUtils;

public class Day08GameConsoleCode {

    public static class Inst {

        String command;

        int value;

        @Override
        public String toString() {
            return "Inst{" +
                    "command='" + command + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<String> stringLines = FileUtils.fileAsStringPerLineToStringList("day08");

        List<Inst> commands = new ArrayList<>();
        SBinder binder = new SBinder("([a-z]+) ([0-9-+]+)", "command", "value");
        for (String line : stringLines) {
            commands.add(binder.bind(line, Inst.class));
        }

        boolean[] execs = new boolean[commands.size()];
        int pointer = 0;
        int accValue = 0;
        while (!execs[pointer]) {
            Inst currentInst = commands.get(pointer);
            execs[pointer] = true;
            if (currentInst.command.equals("nop")) {
                pointer++;
            } else if (currentInst.command.equals("acc")) {
                accValue += currentInst.value;
                pointer++;
            } else if (currentInst.command.equals("jmp")) {
                pointer += currentInst.value;
            }
        }

        Log.part1(accValue);

        for (int index = 0; index < commands.size(); index++) {
            if (commands.get(index).command.equals("nop")) {
                commands.get(index).command = "jmp";
                if (testCode(commands)) {
                    break;
                }
                commands.get(index).command = "nop";
            } else if (commands.get(index).command.equals("jmp")) {
                commands.get(index).command = "nop";
                if (testCode(commands)) {
                    break;
                }
                commands.get(index).command = "jmp";
            }

        }

        Log.part2(0);
    }

    public static boolean testCode(List<Inst> commands) {
        boolean[] execs = new boolean[commands.size() + 1];
        int pointer = 0;
        int accValue = 0;
        while (!execs[pointer] && pointer < commands.size()) {
            Inst currentInst = commands.get(pointer);
            execs[pointer] = true;
            if (currentInst.command.equals("nop")) {
                pointer++;
            } else if (currentInst.command.equals("acc")) {
                accValue += currentInst.value;
                pointer++;
            } else if (currentInst.command.equals("jmp")) {
                pointer += currentInst.value;
            }
        }

        if (pointer >= commands.size()) {
            System.out.println(accValue);
            return true;
        }

        return false;
    }

}
