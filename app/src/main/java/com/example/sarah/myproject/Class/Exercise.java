package com.example.sarah.myproject.Class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarah on 24-Jun-15.
 */
public class Exercise
{
    public String title, description, therapistId, imagePath, filePath, link;
    public int id, folderId, isVideo;
    public static List<Exercise> exerciseList = new ArrayList<>();
    //public boolean isVideo;

    public Exercise(int id, String title, int folderId, String therapistId, String description, String imagePath, String filePath, int isVideo, String link)
    {
        this.description = description;
        this.filePath = filePath;
        this.folderId = folderId;
        this.id = id;
        this.imagePath = imagePath;
        this.isVideo = isVideo;
        this.link = link;
        this.therapistId = therapistId;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", therapistId='" + therapistId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", filePath='" + filePath + '\'' +
                ", link='" + link + '\'' +
                ", id=" + id +
                ", folderId=" + folderId +
                ", isVideo=" + isVideo +
                '}';
    }

    public static void addExercise(Exercise exercise)
    {
        if(exerciseList!=null)
        {
            if(exerciseList.size() == 0)
            {
                exerciseList.add(exercise);
            }
            else if(!exerciseList.contains(exercise))
            {
                exerciseList.add(exercise);
            }
        }

    }

    public static List<Exercise> getExerciseList()
    {
        return exerciseList;
    }
    public static Exercise getExerciseById(int exerciseId)
    {
        if(exerciseList!=null)
        {
            for (int i = 0; i < exerciseList.size(); i++) {
                Exercise exercise = exerciseList.get(i);
                if (exercise.getId() == exerciseId) {
                    return exercise;
                }
            }
        }
        return null;
    }
    public static String getAllExercises()
    {
        String str = "";
        if(exerciseList!=null)
        {
            str += "Size of exercises = " + exerciseList.size() + "\n";
            for(int i= 0; i<exerciseList.size(); i++)
            {
                str += exerciseList.get(i).getId() + "_" + exerciseList.get(i).getTitle() + "\n";
            }
        }
        else
        {
            str += "list empty\n";
        }
        return  str;
    }

}
