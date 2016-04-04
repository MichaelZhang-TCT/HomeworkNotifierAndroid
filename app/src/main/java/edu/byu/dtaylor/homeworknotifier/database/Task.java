package edu.byu.dtaylor.homeworknotifier.database;

/**
 * Created by longl on 4/3/2016.
 */
public class Task {
    String assignmentId;
    String courseId;
    int dueDate;
    int assignedDate;

    public Task(Assignment assignment, int assignedDate)
    {
        this.assignmentId = assignment.getAssignmentId();
        this.courseId = assignment.getCourseId();
        this.dueDate = assignment.getDueDate();
        this.assignedDate = assignedDate;
    }

    public Task(String assignmentId, String courseId, String dueDate, String assignedDate) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.dueDate = Integer.parseInt(dueDate);
        this.assignedDate = Integer.parseInt(assignedDate);
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public int getDueDate() {
        return dueDate;
    }

    public int getAssignedDate() {
        return assignedDate;
    }
}
