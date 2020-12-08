package ws.bmocanu.aoc.ed2020.interp;

import java.util.List;

public class InstructionActions {

    private List<Instruction> instructions;

    public InstructionActions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    // ----------------------------------------------------------------------------------------------------

    public InstructionActions resetExecuted() {
        for (Instruction inst : instructions) {
            inst.executed = false;
        }
        return this;
    }

}