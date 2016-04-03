package edu.byu.dtaylor.homeworknotifier.schedule;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.database.Course;
import edu.byu.dtaylor.homeworknotifier.database.Database;
import edu.byu.dtaylor.homeworknotifier.database.Task;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonAssignment;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonCourse;
import edu.byu.dtaylor.homeworknotifier.gsontools.GsonDatabase;

/**
 * Created by Tanner on 3/9/2016.
 */
public class ScheduleFactory {

    public static Schedule create(String jsonData) {
        Schedule s = new Schedule();
        DateFormat formater = new SimpleDateFormat("y-M-d");
        //TODO
        try {
            JSONArray jsonarray = new JSONArray(jsonData);
            for(int i=0; i<jsonarray.length(); i++){
                JSONObject obj = jsonarray.getJSONObject(i);

                String description = obj.getString("Description");
                Date date = formater.parse(obj.getString("Date"));
                ScheduleItem item = new ScheduleItem(description);
                s.add(item,date);
            }
        }
        catch(JSONException e) {

        }
        catch(ParseException e) {

        }
        return s;
    }

    /**
     * returns null if the database isn't running
     * @param database
     * @return
     */
    public static Schedule create(Database database) {
        Schedule s = new Schedule();
        DateFormat formatter = new SimpleDateFormat("y-M-d");
        //TODO
        int colors[] = new int[7];
        colors[0] = Color.parseColor("#425C8C");
        colors[1] = Color.parseColor("#EE573B");
        colors[2] = Color.parseColor("#44A85E");
        colors[3] = Color.parseColor("#E0C754");
        colors[4] = Color.parseColor("#2D8986");
        colors[5] = Color.parseColor("#B44392");
        colors[6] = Color.parseColor("#808080");
        int current = 0;
        ArrayList<Course> courses = database.getCourses();
        if (database == null || courses == null)
        {
            return null;
        }
        for(Course course : courses){
            int color = colors[current == 6 ? current=0 : current++];
            for(Task task : course.getTasks())
            {
                String description = task.getDescription();
                String name = task.getName();
                Date dueDate = new Date(task.getDueDate());
                String category = task.getCategory();
                String courseID = task.getCourseId();
                boolean graded = task.isGraded();
                double points = task.getPoints();
                String type = task.getType();
                String url = task.getRefUrl();
                double weight = task.getWeight();

                ScheduleItem item = new ScheduleItem(name, description, color, category, courseID, course.getShortTitle(), course.getTitle(), dueDate, graded, points, type, url, weight);

                s.add(item,dueDate);
            }
        }
        return s;
    }
}
