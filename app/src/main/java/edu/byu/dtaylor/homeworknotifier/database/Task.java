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
    int color;

    public Task(String assignmentId, String courseId, String dueDate, String assignedDate, String color) {
        this.assignmentId = assignmentId;
        this.courseId = courseId;
        this.dueDate = Integer.parseInt(dueDate);
        this.assignedDate = Long.parseLong(assignedDate);
        this.color = Integer.parseInt(color);
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

    public int getColor() {
        return color;
    }
}
