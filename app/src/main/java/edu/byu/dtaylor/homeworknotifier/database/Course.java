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
    ArrayList<Task> tasks = new ArrayList<>();
    String courseId;;

    public Course(GsonCourse course)
    {
        courseId = course.getId();
        shortTitle = course.getShortTitle();
        title = course.getTitle();
        for(GsonAssignment assignment : course.getAssignments())
        {
            tasks.add(new Task(assignment));
        }
    }
}
