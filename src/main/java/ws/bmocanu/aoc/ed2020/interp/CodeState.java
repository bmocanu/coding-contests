package ws.bmocanu.aoc.ed2020.interp;

public class CodeState {

    public CodeStatusType status;

    @Override
    public String toString() {
        return "CodeTermination{" +
                "terminationType=" + status +
                '}';
    }
}
