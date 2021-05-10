package com.battaglia.simulatingprocesses;

public class Process {

    private int PID;
    private int priority;
    private int ARRIVALTIME;
    private int DURATION;
    private int waiting;
    private float totalWaiting;

    public Process() {

    }

    public Process(int PID, int priority, int ARRIVALTIME, int DURATION) {
        this.PID = PID;
        this.priority = priority;
        this.ARRIVALTIME = ARRIVALTIME;
        this.DURATION = DURATION;
        this.waiting = 0;
        this.totalWaiting = 0;
    }

    public int getPID() {
        return PID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getARRIVALTIME() {
        return ARRIVALTIME;
    }

    public int getDURATION() {
        return DURATION;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    public float getTotalWaiting() {
        return totalWaiting;
    }

    public void setTotalWaiting(float totalWaiting) {
        this.totalWaiting = totalWaiting;
    }

    @Override
    public String toString() {
        return  "ID = " + PID +
                ",\tPriority = " + priority +
                ",\tDuration = " + DURATION +
                ",\tArrival time = " + ARRIVALTIME +
                ",\tWait time = " + waiting;
    }
}
