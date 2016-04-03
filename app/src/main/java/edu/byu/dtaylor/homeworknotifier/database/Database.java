package edu.byu.dtaylor.homeworknotifier.database;

import java.util.ArrayList;

import edu.byu.dtaylor.homeworknotifier.gsontools.GsonCourse;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

/**
 * Created by longl on 4/3/2016.
 */
public class Database {
    ArrayList<Course> courses = new ArrayList<>();
    String userId;

    public Database(GsonDatabase db)
    {
        userId = db.getUser().getId();
        for(GsonCourse course : db.getUser().getCourses())
        {
            courses.add(new Course(course));
        }
    }
}
