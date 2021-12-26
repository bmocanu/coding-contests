package ws.bmocanu.aoc.ed2021;

import ws.bmocanu.aoc.utils.XRead;
import ws.bmocanu.aoc.xbase.SolutionBase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day24_SecondTry extends SolutionBase {

    public static void main(String[] args) throws IOException {
        List<String> inputLines = XRead.fileAsStringPerLineToStringList(filePath("day24"));
        StringBuilder builder = new StringBuilder(1000);
        builder.append("public class Runner {\n" +
                "\n" +
                "    static int[] number = new int[14];\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        long currentNr = Long.parseLong(args[0]);\n" +
                "        int w, x, y, index = 0;\n" +
                "        int z = 1;\n" +
                "\n" +
                "        monad_loop:\n" +
                "        while (z != 0) {\n" +
                "            w = x = y = z = 0;\n" +
                "            currentNr--;\n" +
                "            System.out.println(currentNr);\n" +
                "            long splitNr = currentNr;\n" +
                "            for (int digitIndex = 13; digitIndex >= 0; digitIndex--) {\n" +
                "                if (splitNr % 10 == 0) {\n" +
                "                    continue monad_loop;\n" +
                "                }\n" +
                "                number[digitIndex] = (int) splitNr % 10;\n" +
                "                splitNr = splitNr / 10;\n" +
                "            }\n" +
                "            index = 0;\n");
        for (String line : inputLines) {
            if (line.contains("-----")) {
                continue;
            }
            String[] parts = line.split(" ");
            if (line.startsWith("inp")) {
                builder.append("            " + parts[1] + " = number[index]; index++;\n");
            }
            if (line.startsWith("mul")) {
                builder.append("            " + parts[1] + " = " + parts[1] + " * " + parts[2] + ";\n");
            }
            if (line.startsWith("add")) {
                builder.append("            " + parts[1] + " = " + parts[1] + " + " + parts[2] + ";\n");
            }
            if (line.startsWith("mod")) {
                builder.append("            " + parts[1] + " = " + parts[1] + " % " + parts[2] + ";\n");
            }
            if (line.startsWith("div")) {
                builder.append("            " + parts[1] + " = " + parts[1] + " / " + parts[2] + ";\n");
            }
            if (line.startsWith("eql")) {
                builder.append("            " + parts[1] + " = " + parts[1] + " == " + parts[2] + " ? 1 : 0;\n");
            }
        }
        builder.append("}}}");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:/Runner.java"));
        bos.write(builder.toString().getBytes(StandardCharsets.UTF_8));
        bos.close();
    }

}
