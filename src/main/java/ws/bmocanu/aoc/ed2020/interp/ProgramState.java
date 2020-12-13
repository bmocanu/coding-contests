package ws.bmocanu.aoc.ed2020.interp;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ProgramState {

    public ProgramStatusType status;

    public boolean finished() {
        return status == ProgramStatusType.FINISHED_OK || status == ProgramStatusType.FINISHED_LOOP;
    }

    public boolean finishedOk() {
        return status == ProgramStatusType.FINISHED_OK;
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "status=" + status +
                '}';
    }
}
