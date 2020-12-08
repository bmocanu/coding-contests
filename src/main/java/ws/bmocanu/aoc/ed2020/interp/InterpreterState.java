package ws.bmocanu.aoc.ed2020.interp;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class InterpreterState {

    public StatusType status;

    public boolean finished() {
        return status == StatusType.FINISHED_OK || status == StatusType.FINISHED_LOOP;
    }

    public boolean finishedOk() {
        return status == StatusType.FINISHED_OK;
    }

    @Override
    public String toString() {
        return "InterpreterState{" +
               "status=" + status +
               '}';
    }
}
