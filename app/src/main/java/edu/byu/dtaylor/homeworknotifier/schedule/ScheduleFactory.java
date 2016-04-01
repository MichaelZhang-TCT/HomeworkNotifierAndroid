package edu.byu.dtaylor.homeworknotifier.schedule;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.dtaylor.homeworknotifier.gsontools.Assignment;
import edu.byu.dtaylor.homeworknotifier.gsontools.Course;
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
    public static Schedule create(GsonDatabase database) {
        Schedule s = new Schedule();
        DateFormat formater = new SimpleDateFormat("y-M-d");
        //TODO
        int colors[] = new int[6];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.YELLOW;
        colors[4] = Color.MAGENTA;
        colors[5] = Color.DKGRAY;
        int current = 0;
        for(Course course : database.getUser().getCourses()){
            int color = colors[current++];
            for(Assignment assignment : course.getAssignments())
            {
                String description = assignment.getDescription();
                String name = assignment.getName();
                Date date = new Date(assignment.getDueDate());
                ScheduleItem item = new ScheduleItem(name, description, color);
                s.add(item,date);
            }
        }
        return s;
    }
}
