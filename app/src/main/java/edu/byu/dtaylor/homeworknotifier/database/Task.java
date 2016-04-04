package edu.byu.dtaylor.homeworknotifier.database;

import edu.byu.dtaylor.homeworknotifier.MainActivity;

/**
 * Created by longl on 4/3/2016.
 */
public class Task {
    String assignmentId;
    String courseId;
    int dueDate;
    long assignedDate;

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
        this.assignedDate = Long.parseLong(assignedDate);
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

    public long getAssignedDate() {
        return assignedDate;
    }

    public Assignment getAssignment() {
        for(Course course : MainActivity.database.getCourses())
        {
            if(course.getCourseId().equals(courseId))
            {
                for(Assignment assignment : course.getAssignments())
                {
                    if(assignment.getAssignmentId().equals(assignmentId))
                    {
                        return assignment;
                    }
                }
            }
        }
        return null;
    }
}
