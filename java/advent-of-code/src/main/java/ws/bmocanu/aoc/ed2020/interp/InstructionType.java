package ws.bmocanu.aoc.ed2020.interp;

public enum InstructionType {

    NOP("nop"),
    JMP("jmp"),
    ACC("acc");

    private final String value;

    InstructionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static InstructionType getByValue(String value) {
        for (InstructionType currentType : values()) {
            if (currentType.getValue().equals(value)) {
                return currentType;
            }
        }
        throw new IllegalArgumentException("Unknown instruction type value: [" + value + "]");
    }

}
