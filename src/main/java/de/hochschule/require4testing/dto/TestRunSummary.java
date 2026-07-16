package de.hochschule.require4testing.dto;

public class TestRunSummary {
    private String runName;
    private String runStatus;
    private long total;
    private long passed;
    private long failed;
    private long blocked;
    private long skipped;
    private long retest;
    private long notExecuted;
    private long executed;
    private int passRatePercent;
    private int progressPercent;

    public String getRunName() { return runName; }
    public void setRunName(String runName) { this.runName = runName; }
    public String getRunStatus() { return runStatus; }
    public void setRunStatus(String runStatus) { this.runStatus = runStatus; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getPassed() { return passed; }
    public void setPassed(long passed) { this.passed = passed; }
    public long getFailed() { return failed; }
    public void setFailed(long failed) { this.failed = failed; }
    public long getBlocked() { return blocked; }
    public void setBlocked(long blocked) { this.blocked = blocked; }
    public long getSkipped() { return skipped; }
    public void setSkipped(long skipped) { this.skipped = skipped; }
    public long getRetest() { return retest; }
    public void setRetest(long retest) { this.retest = retest; }
    public long getNotExecuted() { return notExecuted; }
    public void setNotExecuted(long notExecuted) { this.notExecuted = notExecuted; }
    public long getExecuted() { return executed; }
    public void setExecuted(long executed) { this.executed = executed; }
    public int getPassRatePercent() { return passRatePercent; }
    public void setPassRatePercent(int passRatePercent) { this.passRatePercent = passRatePercent; }
    public int getProgressPercent() { return progressPercent; }
    public void setProgressPercent(int progressPercent) { this.progressPercent = progressPercent; }
}