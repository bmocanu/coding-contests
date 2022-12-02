package ws.bmocanu.aoc.ed2020.interp;

public class Instruction {

    public InstructionType type;

    public int value;

    public boolean executed;

    // ----------------------------------------------------------------------------------------------------

    public Instruction deepClone() {
        Instruction newInst = new Instruction();
        newInst.type = type;
        newInst.value = value;
        newInst.executed = executed;
        return newInst;
    }

    // ----------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "CodeLine{" +
               "instruction=" + type +
               ", value=" + value +
               ", executed=" + executed +
               '}';
    }
}
