package edu.byu.dtaylor.homeworknotifier.schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
