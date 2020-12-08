package ws.bmocanu.aoc.ed2020.interp;

public class CodeLine {

    public InstructionType instruction;

    public int value;

    public boolean executed;

    // ----------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "CodeLine{" +
                "instruction=" + instruction +
                ", value=" + value +
                ", executed=" + executed +
                '}';
    }
}
