package edu.byu.dtaylor.homeworknotifier.database;

import java.util.ArrayList;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonCourse;

/**
 * Created by longl on 4/3/2016.
 */
public class Course {
    private String shortTitle;
    private String title;
    ArrayList<Assignment> assignments = new ArrayList<>();
    String courseId;;
    private GsonAssignment[] assignments;

    public Course(GsonCourse course)
    {
        courseId = course.getId();
        shortTitle = course.getShortTitle();
        title = course.getTitle();
        for(GsonAssignment assignment : course.getAssignments())
        {
            assignments.add(new Assignment(assignment));
        }
    }

    public Course(String courseId, String courseTitle, String courseShortTitle) {
        this.shortTitle = courseShortTitle;
        this.courseId = courseId;
        this.title = courseTitle;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public GsonAssignment[] getAssignments() {
        return assignments;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseId() {
        return courseId;
    }
}
