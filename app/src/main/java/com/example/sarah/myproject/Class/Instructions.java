package com.example.sarah.myproject.Class;

/**
 * Created by Sarah on 10-Jan-15.
 */
public class Instructions
{
    String exersiceInstruction;
    int priority;

    public Instructions(String exersiceInstruction, int priority) {
        this.exersiceInstruction = exersiceInstruction;
        this.priority = priority;
    }

    public String getExersiceInstruction() {
        return exersiceInstruction;
    }

    public void setExersiceInstruction(String exersiceInstruction) {
        this.exersiceInstruction = exersiceInstruction;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Instructions{" +
                "exersiceInstruction='" + exersiceInstruction + '\'' +
                ", priority=" + priority +
                '}';
    }
}
