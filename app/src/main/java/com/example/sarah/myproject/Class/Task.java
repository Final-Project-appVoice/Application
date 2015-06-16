package com.example.sarah.myproject.Class;

/**
 * Created by Sarah on 08-Jun-15.
 */
public class Task
{
    private int taskId, exerciseId;
    private String taskTitle, description, imagePath, comment;
    private boolean isDone;

    public Task(int taskId, String taskTitle, String comment, String description, int exerciseId, String imagePath, boolean isDone)
    {
        this.taskTitle = taskTitle;
        this.taskId = taskId;
        this.comment = comment;
        this.description = description;
        this.exerciseId = exerciseId;
        this.imagePath = imagePath;
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    @Override
    public String toString() {
        return "Task{" +
                "comment='" + comment + '\'' +
                ", taskId=" + taskId +
                ", exerciseId=" + exerciseId +
                ", taskTitle='" + taskTitle + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
