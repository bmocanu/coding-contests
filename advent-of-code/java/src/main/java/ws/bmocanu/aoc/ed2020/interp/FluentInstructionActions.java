package ws.bmocanu.aoc.ed2020.interp;

import java.util.List;

public class FluentInstructionActions {

    private List<Instruction> instructions;

    public FluentInstructionActions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    // ----------------------------------------------------------------------------------------------------

    public FluentInstructionActions resetExecuted() {
        for (Instruction inst : instructions) {
            inst.executed = false;
        }
        return this;
    }

}