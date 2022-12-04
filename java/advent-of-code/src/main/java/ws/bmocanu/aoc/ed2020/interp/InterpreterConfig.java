package ws.bmocanu.aoc.ed2020.interp;

public class InterpreterConfig {

    public boolean runLog = false;

    public boolean loadDataLog = false;

    public InterpreterConfig enableRunLog() {
        runLog = true;
        return this;
    }

    public InterpreterConfig enableLoadDataLog() {
        loadDataLog = true;
        return this;
    }

    public InterpreterConfig deepClone() {
        InterpreterConfig newConfig = new InterpreterConfig();
        newConfig.runLog = runLog;
        newConfig.loadDataLog = loadDataLog;
        return newConfig;
    }

}
