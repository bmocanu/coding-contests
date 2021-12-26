package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.flex.FlexNumber;
import ws.bmocanu.aoc.support.Log;
import ws.bmocanu.aoc.utils.SList;
import ws.bmocanu.aoc.utils.SMap;
import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.utils.XUtils;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.util.List;

public class Day24_FirstTry extends SolutionBase {

    static SMap<String, Integer> registries = new SMap<>();
    static SList<Instruction> instructions;

    public static void main(String[] args) {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day24"));
        instructions = new SList<>();
        for (String line : inputLines) {
            if (line.contains("-----")) {
                continue;
            }
            String[] parts = line.split(" ");
            if (line.startsWith("inp")) {
                Input i = new Input();
                i.registry = parts[1];
                instructions.add(i);
            }
            if (line.startsWith("mul")) {
                Multiply i = new Multiply();
                i.registry = parts[1];
                i.data = getRegistryOrValueRead(parts[2]);
                instructions.add(i);
            }
            if (line.startsWith("add")) {
                Add i = new Add();
                i.registry = parts[1];
                i.data = getRegistryOrValueRead(parts[2]);
                instructions.add(i);
            }
            if (line.startsWith("mod")) {
                Mod i = new Mod();
                i.registry = parts[1];
                i.data = getRegistryOrValueRead(parts[2]);
                instructions.add(i);
            }
            if (line.startsWith("div")) {
                Div i = new Div();
                i.registry = parts[1];
                i.data = getRegistryOrValueRead(parts[2]);
                instructions.add(i);
            }
            if (line.startsWith("eql")) {
                Equal i = new Equal();
                i.registry = parts[1];
                i.data = getRegistryOrValueRead(parts[2]);
                instructions.add(i);
            }
        }

        // ---------- XXXXXXXXXXXXXX
        long number = 92793949999999L;
//        runMonad(number);
        while (!runMonad(number)) {
            System.out.println(number);
            number--;
            while (String.valueOf(number).contains("0")) {
                number--;
            }
        }
        Log.part1(number);
    }

    // ----------------------------------------------------------------------------------------------------

    static boolean runMonad(long number) {
        long localNumber = number;
        for (int digitIndex = 13; digitIndex >= 0; digitIndex--) {
            inputNumber[digitIndex] = (int) (localNumber % 10);
            localNumber = localNumber / 10;
        }
        registries.put("w", 0);
        registries.put("x", 0);
        registries.put("y", 0);
        registries.put("z", 0);
        inputIndex = 0;
        for (Instruction ins : instructions) {
            ins.execute();
        }
        return registries.get("z") == 0;
    }

    static DataRead getRegistryOrValueRead(String str) {
        if (XUtils.stringIsNumber(str)) {
            ValueRead vr = new ValueRead();
            vr.value = Integer.parseInt(str);
            return vr;
        } else {
            RegistryRead rr = new RegistryRead();
            rr.registry = str;
            return rr;
        }
    }

    // ----------------------------------------------------------------------------------------------------

    static int[] inputNumber = new int[14];
    static int inputIndex;

    interface Instruction {
        void execute();
    }

    static class Input implements Instruction {
        String registry;

        @Override
        public void execute() {
//            System.out.println("----------------------------------------------");
//            System.out.println(String.format("w=%12d | x=%12d | y=%12d | z=%12d",
//                    registries.get("w"), registries.get("x"), registries.get("y"), registries.get("z")));
//            System.out.println("----------------------------------------------");
            registries.put(registry, inputNumber[inputIndex]);
            inputIndex++;
        }
    }

    static class Mod implements Instruction {
        String registry;
        DataRead data;

        @Override
        public void execute() {
            registries.put(registry, registries.get(registry) % data.getValue());
        }
    }

    static class Multiply implements Instruction {
        String registry;
        DataRead data;

        @Override
        public void execute() {
            registries.put(registry, registries.get(registry) * data.getValue());
        }
    }

    static class Div implements Instruction {
        String registry;
        DataRead data;

        @Override
        public void execute() {
            registries.put(registry, registries.get(registry) / data.getValue());
        }
    }

    static class Equal implements Instruction {
        String registry;
        DataRead data;

        @Override
        public void execute() {
            int valueA = registries.get(registry);
            int valueB = data.getValue();
            registries.put(registry, valueA == valueB ? 1 : 0);
        }
    }

    static class Add implements Instruction {
        String registry;
        DataRead data;

        @Override
        public void execute() {
            registries.put(registry, registries.get(registry) + data.getValue());
        }
    }

    interface DataRead {
        int getValue();
    }

    static class RegistryRead implements DataRead {
        String registry;

        @Override
        public int getValue() {
//            System.out.println("Read [" + registry + "]");
            return registries.get(registry);
        }
    }

    static class ValueRead implements DataRead {
        int value;

        @Override
        public int getValue() {
            return value;
        }
    }

}
