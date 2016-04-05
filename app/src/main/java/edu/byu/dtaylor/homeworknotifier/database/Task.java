package edu.byu.dtaylor.homeworknotifier.database;

/**
 * Created by longl on 4/3/2016.
 */
public class Task {
    String assignmentId;
    String courseId;
    long assignedDate;
    int color;
    boolean completed;

    public Task(String assignmentId, String courseId, String completed, String assignedDate, String color) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.assignedDate = Long.parseLong(assignedDate);
        this.color = Integer.parseInt(color);
        this.completed = Boolean.parseBoolean(completed);
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getCourseId() {
        return courseId;
    }


    public long getAssignedDate() {
        return assignedDate;
    }

    public int getColor() {
        return color;
    }

    public boolean isCompleted() {
        return completed;
    }
}
